package mlscript.compiler.wasm

import mlscript.compiler.wasm.Type as MachineType
import mlscript.utils.shorthands.*

// constant-time lazy reference counting (CTRC)
object Refcount:

  import RecordObj.*
  import MachineInstr.*
  import Symbol.*

  // each allocation is done in unit cell with 64byte
  final val cellSize = 64
  final val freeList = "free_list"

  @inline def roundUp(size: Int): Int =
    (size + cellSize - 1) & ~(cellSize - 1)

  // compute the maximum number of fields given number of cells
  @inline def computeMaxFields(numCells: Int) =
    if numCells == 0 then
      0
    else
      val numEach = (cellSize - getHeaderSize) / defaultFieldSize
      numCells * (numEach - 1) + 1

  // compute the num of cells the object needs,
  // object metadata header is expected to be included in the size
  def splitObject(size: Int): Int =
    val numEach = (cellSize - getHeaderSize) / defaultFieldSize
    assert(numEach > 2, s"available acutal storage should larger than 2 * ${defaultFieldSize} B")
    val numVirtualFields = (size - getHeaderSize) / defaultFieldSize
    if numVirtualFields <= numEach then
      1
    else
      (numVirtualFields - 1 + numEach - 2) / (numEach - 1)

  // compute the offset in CTRC setting (cell index, raw offset in the cell)
  // object metadata header is expected to be included in the size
  def splitOffset(offset: Int): (Int, Int) =
    // note that the actuall size include `this` field
    val numFieldsNeed = RecordObj.revertOffset2Index(offset) + 1
    val numCells = splitObject(offset + defaultFieldSize)
    val previousFields = computeMaxFields(numCells - 1)
    val remainingFields = numFieldsNeed - previousFields
    val remainingOffset = RecordObj.getFieldOffset(remainingFields - 1)
    (numCells - 1, remainingOffset)
        

  def generateIncRef: MachineFunction =
    val refcntOffset = RecordObj.getRefCountOffset
    val ref = "ref"
    val body = Ls(
      Block("check_pointer", Ls(MachineType.Void)),
        GetLocal(ref),
        I32Const(1),
        I32And,
        BrIf(0),
        GetLocal(ref),
        GetLocal(ref),
        I32LdOffset(ImmOffset(refcntOffset)),
        I32Const(1),
        I32Add,
        I32StOffset(ImmOffset(refcntOffset)),
      End
    )

    val paramsType = Ls(ref) map { param => param -> MachineType.defaultNumType}
    // local info collect when codegen
    val localsType = Ls()
    val retType = Ls(MachineType.Void)
    MachineFunction(
      builtinIncRef,
      paramsType,
      localsType,
      retType,
      body
    )

  def generateDecRef: MachineFunction =
    val refcntOffset = RecordObj.getRefCountOffset
    val ref = "ref"
    val refv = "v"
    val body = Ls(
      Block("check_refcnt", Ls(MachineType.Void)),
        GetLocal(ref),
        I32LdOffset(ImmOffset(refcntOffset)),
        I32Const(-1),
        I32Add,
        TeeLocal(refv),
        Comment(s"dec refcnt in ${ref}"),
        BrIf(0),
        // branch to free object
        GetLocal(ref),
        Call(builtinFree),
        Return,
      End,
        GetLocal(ref),
        GetLocal(refv),
        I32StOffset(ImmOffset(refcntOffset))
    )
    
    val paramsType = Ls(ref) map { param => param -> MachineType.defaultNumType}
    // local info collect when codegen
    val localsType = Ls(refv) map {local => local -> MachineType.defaultNumType}
    val retType = Ls(MachineType.Void)
    MachineFunction(
      builtinDecRef,
      paramsType,
      localsType,
      retType,
      body
    )

  def generateReuse: MachineFunction =
    /*
      struct cell_t *reuse_cell() {
        if (free_list->next != (void *)0) {
          struct cell_t *block = free_list->next;
          free_list->next = block->next;
          // free fields
          for (int i = header_size; i < block_size; i += 4) {
            // check if it is a pointer
            if (( (   *((int*)(((char *)block) + i)) & 1)) == 0) {
              decref(((char *)block) + i);
            }
          }
          return block;
        } else {
          struct cell_t *block = (struct cell_t *)gp;
          gp += block_size;
          return block;
        }
      }
     */
    val block = "block"
    val iter = "i"
    val fieldPtr = "pfield"

    val body = Ls(
      Block("check_null", Ls(MachineType.Void)),
        Block("free_branch", Ls(MachineType.Void)),
          I32Const(0),
          I32LdOffset(LabelAddr(freeList)),
          TeeLocal(block),
          I32Eqz,
          BrIf(0), // down to label 0
          I32Const(0),
          GetLocal(block),
          I32Load,
          I32StOffset(LabelAddr(freeList)),
          // set iteration loop
          I32Const(getHeaderSize),
          SetLocal(iter),
          Loop("deref_iter"), // label 2
            GetLocal(iter),
            I32Const(cellSize - 1),
            I32Gt(signed = false),
            BrIf(2), // down to label 0
            Block("check_pointer", Ls(MachineType.Void)),
              GetLocal(block),
              GetLocal(iter),
              I32Add,
              TeeLocal(fieldPtr),
              I32Load8_u,
              I32Const(1),
              I32And,
              BrIf(0), // down to label 3
              GetLocal(fieldPtr),
              Call(builtinDecRef),
            End, // label 3
            GetLocal(iter),
            I32Const(defaultFieldSize),
            I32Add,
            SetLocal(iter),
            Br(0), // up to label 2
          End,
        End, // label 1
        GetGlobal(0),
        TeeLocal(block),
        I32Const(cellSize),
        I32Add,
        SetGlobal(0),
      End, // label 0
      GetLocal(block)
    )

    val paramsType = Ls()
    // local info collect when codegen
    val localsType = Ls(block, iter, fieldPtr) map {local => local -> MachineType.defaultNumType}
    val retType = Ls(MachineType.defaultNumType)
    MachineFunction(
      builtinReuse,
      paramsType,
      localsType,
      retType,
      body
    )

  def generateMalloc: MachineFunction =
    val size = "size"
    val block = "block"
    val body = Ls(
      I32Const(-128),
      SetLocal(block),
      Block("fast_path_small_obj", Ls(MachineType.Void)),
        GetLocal(size),
        I32Const(cellSize),
        I32Gt(signed = true),
        BrIf(0),
        Call(builtinReuse),
        TeeLocal(block),
        Call(builtinIncRef),
      End,
      GetLocal(block)
    )
    
    val paramsType = Ls(size) map { param => param -> MachineType.defaultNumType}
    // local info collect when codegen
    val localsType = Ls(block) map {local => local -> MachineType.defaultNumType}
    val retType = Ls(MachineType.defaultNumType)
    MachineFunction(
      builtinMalloc,
      paramsType,
      localsType,
      retType,
      body
    )

  def generateFree: MachineFunction =
    /* 
      void LazyFree(struct cell_t *p) {
        struct cell_t *temp = free_list->next;
        p->next = temp;
        free_list->next = p;
      }
     */
    val ref = "ref"
    val temp = "temp"
    val body = Ls(
      GetLocal(temp),
      I32Const(0),
      I32LdOffset(LabelAddr(freeList)),
      I32Store,
      I32Const(0),
      GetLocal(temp),
      I32StOffset(LabelAddr(freeList))
    )

    val paramsType = Ls(ref) map { param => param -> MachineType.defaultNumType}
    // local info collect when codegen
    val localsType = Ls(temp) map {local => local -> MachineType.defaultNumType}
    val retType = Ls(MachineType.Void)
    MachineFunction(
      builtinFree,
      paramsType,
      localsType,
      retType,
      body
    )


  // register neccessary data structures to data segments
  def registerAllocator(segment: DataSegment) =
    segment.register("free_list", DataString.zeros(1))
