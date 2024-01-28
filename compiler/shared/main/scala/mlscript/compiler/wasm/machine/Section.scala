package mlscript.compiler.wasm

import mlscript.compiler.wasm.Type as MachineType

import mlscript.compiler.*
import mlscript.utils.*
import mlscript.utils.shorthands.*
import mlscript.*

import scala.collection.mutable.{ArrayBuffer, Map => MutMap}

// encoding data in wasm data segments
class DataString(
  val data: Array[Byte]
):
  override def toString(): String =
    data map { byte => f"\\${byte}%02x"} mkString ""

  def concate(rhs: DataString): DataString =
    DataString(this.data.concat(rhs.data))

  def size =
    data.size

object DataString:

  final val byteWidth = 8

  // little-endian encoding
  @inline private def parseInt(value: Int) =
      f"${value}%08x".sliding(2, 2).map(Integer.parseInt(_, 16).toByte).toArray.reverse

  def fromInt(value: Int) =
    DataString(parseInt(value))
    
  def fromIntSeq(seq: IterableOnce[Int]) =
    DataString(seq.iterator.flatMap(parseInt).toArray)
  
  def zeros(size: Int, width: Int = 32) =
    DataString(Array.fill(size * (width / byteWidth))(0))

  def fromString(str: Str) =
    DataString(str.getBytes())

class DataSegment(
  segment: MutMap[String, DataString]
):
  import DataSegment.*

  def computeOffsets: List[(String, (Int, DataString))] =
    var size = 0
    segment.toList.map {
      case (name, data) =>
        val offset = size
        size = size + roundUp(data.size)
        name -> (offset, data)
    }

  def memorySize: Int =
    segment.values.foldLeft(0) {
      case (size, data) => size + roundUp(data.size)
    }
  
  def register(name: String, data: DataString) =
    if segment.contains(name) then
      throw WasmBackendError(s"try to register duplicated data symbol ${name}")
    else
      segment.update(name, data)
  
  def registerAll(xs: IterableOnce[(String, DataString)]) =
    xs.iterator.foreach(register)


object DataSegment:
  // alignment should consistent with word size and multiples of 4B
  final val alignment = 4

  @inline def roundUp(size: Int) =
    (size + (alignment - 1)) & ~(alignment - 1)

  def empty =
    DataSegment(MutMap())


// manage compiler synthesized functions, 
// hand coded WASM
class SynthFunctions(
  ctx: MutMap[Str, MachineFunction]
):

  def contains(key: Str) =
    ctx.contains(key)
  
  def apply(key: Str) =
    ctx(key)

  def getAllFunctions = ctx.values.toList

  def getBuiltinFn(name: Str) =
    ctx(name)

  def registerFn(name: Str, fn: MachineFunction) =
    ctx.update(name, fn)


object SynthFunctions:
  import MachineInstr.*
  import MachineType.*
  import Symbol.*

  def empty =
    SynthFunctions(MutMap.empty)
  
  // handcrafted wasm, performing linear search on interface table of object,
  // return address of found trait vtable
  def generateTraitSearch =
      /*
      * ptr -> header | vtable | itable | <fileds>
      *                           |
      * itable size | (trait id 1, vtable 1) | (trait id 2, vtable 2) | ... 
      *  __mlscript_invokeinterface (this*, trait id) 
      */
    val objParam = "this"
    val traitIdParam = "id"
    val idxLocal = "idx"
    val sizeLocal = "size"
    val iptrLocal = "iptr"
    val scanLocal = "scan"
    val headerTem = RecordObj.defaultFieldSize
    val scanTem = RecordObj.defaultFieldSize * 2
    
    val instrs = Ls(
      GetLocal(objParam),
      I32Const(RecordObj.getPItableOffset),
      I32Add,
      I32Load,
      SetLocal(iptrLocal),
      Comment("repr impl traits"),
      // size = [iptr] * scanTem + header
      GetLocal(iptrLocal),
      I32Load,
      I32Const(scanTem),
      I32Mul,
      I32Const(headerTem),
      I32Add,
      SetLocal(sizeLocal),

      Comment("size of traits list"),
      // idx = -1
      I32Const(-1),
      SetLocal(idxLocal),
      // scan = iptr
      GetLocal(iptrLocal),
      I32Const(headerTem),
      I32Add,
      SetLocal(scanLocal),

      Block("itable_search", Ls(MachineType.Void)),
        Loop("itable_loop"),
          // while scan < size
          GetLocal(scanLocal),
          GetLocal(sizeLocal),
          I32Ge(signed = false),
          BrIf(1),
          // check
          GetLocal(scanLocal),
          I32Load,
          Comment("load trait id in itable"),
          GetLocal(traitIdParam),
          I32Eq,
          If_void,
            GetLocal(scanLocal),
            I32Const(RecordObj.defaultFieldSize),
            I32Add,
            I32Load,
            SetLocal(idxLocal),
          End,
          // scan = scan + scanTem
          GetLocal(scanLocal),
          I32Const(scanTem),
          I32Add,
          SetLocal(scanLocal),
          Br(0),
        End,
      End,
      // return address of trait vtable
      GetLocal(idxLocal)
    )

    val paramsType = Ls(objParam, traitIdParam) map { param => param -> MachineType.defaultNumType}
    // local info collect when codegen
    val localsType = Ls(idxLocal, sizeLocal, iptrLocal, scanLocal) map {local => local -> MachineType.defaultNumType}
    val retType = Ls(MachineType.defaultNumType)
    MachineFunction(
      builtinItableSearch,
      paramsType,
      localsType,
      retType,
      instrs
    )
  
  def generateShow(classes: Set[ClassInfo]) =
    val objParam = "this"
    val numClasses = classes.size

    val checkInt = Ls(
      Block("check_int_or_pointer", Ls(MachineType.Void)),
      GetLocal(objParam),
      I32Const(1),
      I32And,
      I32Eqz,
      BrIf(0),
      GetLocal(objParam),
      I32Const(1),
      I32Shr(signed = true),
      Call("log_int"),
      Return,
      End
    )

    val getId = Ls(
      Block("match_id", Ls(MachineType.Void)),
      GetLocal(objParam),
      I32Load,
      BrTable(Ls.range(0, numClasses + 1)),
      End,
    )
    val dispath = classes.toList.sorted.foldLeft(getId)(
      (cases, cls) =>
        Ls(
          Block(f"case_${cls.id}", Ls(MachineType.Void)),
          Comment(f"case class ${cls.ident}")
        ) ++
        cases ++
        Ls(
          GetLocal("this"),
          Call(objectShow(cls.ident)),
          Return,
          End,
        )
    )
    val fallback = Ls(
      LdSym(LabelAddr(builtinUndefined)),
      I32Const(builtinUndefined.size),
      Call("log_str")
    )

    val paramsType = Ls(objParam) map { param => param -> MachineType.defaultNumType}
    // local info collect when codegen
    val localsType = Ls()
    val retType = Ls()
    MachineFunction(
      generalShow,
      paramsType,
      localsType,
      retType,
      checkInt ++ dispath ++ fallback
    )    