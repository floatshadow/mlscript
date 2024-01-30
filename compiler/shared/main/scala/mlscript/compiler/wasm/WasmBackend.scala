package mlscript.compiler.wasm

import mlscript.compiler.wasm.Type as MachineType

import mlscript.compiler.*
import mlscript.utils.*
import mlscript.utils.shorthands.*
import mlscript.*

import collection.mutable.{Map => MutMap, Set => MutSet, ListBuffer}


final case class WasmBackendError(message: String) extends Exception(message)


// TODO: optimize the assumption the all value are boxed
class WasmBackend(

):
  import MachineInstr.*
  import GOExpr.*
  import GONode.*
  import ClassMember.*
  import Symbol.*
  import Refcount.*

  case class JoinPoint(
    params: Ls[Name], 
    rhs: GONode,
    outerBlocks: Int = 0,
    needPassArg: Bool = true
  ):
    def passArgs(args: Ls[TrivialExpr]): Ls[MachineInstr] =
      if needPassArg then
        val evalArgs = params.zip(args) flatMap {
          case (Name(paramName), expr) =>
            translateTrivialExpr(expr)
            :+ SetLocal(paramName)
            :+ Comment(s"set ${paramName}")
        }
        evalArgs
      else
        Ls()

    def apply(): Ls[MachineInstr] =
      // store args in a local variable
      translateNode(rhs, outerBlocks)

    // TODO: possible inlining optimization
    private def inlineArgs(args: Ls[TrivialExpr]) =
      val numParams = params.size
      val paramNames = params.map(param => param.str).zip(List.range(0, numParams)).toSet
      rhs |> inlineNodeRec

      def inlineRefName(name: Name): Name =
        val str = name.str
        paramNames.collectFirst {
          case (paramName, index) if str.equals(paramName) =>
            args(index) match
              case Ref(Name(argName)) => Name(argName)
              case _ => throw WasmBackendError(s"try to inline a left value ref ${paramName}")
        } getOrElse(name)
      
      def inlineTrivialExpr(expr: TrivialExpr): TrivialExpr = expr match
        case Ref(Name(name)) =>
          paramNames.collectFirst {
            case (paramName, index) if name.equals(paramName) =>
              args(index)
          } getOrElse(expr)
        case _ => expr
      
      def inlineExpr(expr: GOExpr): GOExpr = expr match
        // very hacky and dangerous, I have to admit
        case s: TrivialExpr => inlineTrivialExpr(s).asInstanceOf[GOExpr]
        case CtorApp(cls, args) =>
          CtorApp(cls, args map inlineTrivialExpr)
        case Select(name, cls, field) =>
          Select(inlineRefName(name), cls, field)
        case BasicOp(op, args) =>
          BasicOp(op, args map inlineTrivialExpr)
        case _ => expr

      def inlineNodeRec(node: GONode): GONode = node match
        case Result(res) =>
          Result(res map inlineTrivialExpr)
        case Jump(joinName, args) =>
          Jump(joinName, args map inlineTrivialExpr)
        case Case(struct, cases) =>
          Case(inlineRefName(struct), cases map {
            case (cls, node) => (cls, node |> inlineNodeRec)
          })
        case LetExpr(name, expr, body) =>
          LetExpr(name, expr |> inlineExpr, body |> inlineNodeRec)
        case LetJoin(joinName, params, rhs, body) =>
          // join point should not ref outside the params
          LetJoin(joinName, params map inlineRefName, rhs, body |> inlineNodeRec)
        case LetCall(resultNames, defn, args, body) =>
          LetCall(resultNames, defn, args map inlineTrivialExpr, body |> inlineNodeRec)
  

  val joinPoints: MutMap[Str, JoinPoint] = MutMap()
  var locals: Set[Str] = Set()
  // classinfo context
  var clsctx: Map[Str, ClassInfo] = Map()
  // top level def context
  var fDefCtx: Map[Int, GODef] = Map()
  // global vtable and itable
  var vtableCtx: Map[Str, (Int, Ls[(Str, MethodInfo)])] = Map()
  // global type table for `call_indirect`
  var typeCtx: Map[Str, MachineType] = Map()
  var layoutCtx: Map[Str, RecordObj] = Map()

  // data sections, contains class runtime representation,
  // allocator/reference counting data strutures, etc.
  val dataCtx = DataSegment.empty
  val builtinCtx = SynthFunctions.empty

  def getFunctionType(godef: GODef) =
    val params = godef.params
    val resultNum = godef.resultNum
    val paramTy = List.fill(params.size)(MachineType.defaultNumType)
    val resultTy = List.fill(resultNum)(MachineType.defaultNumType)
    MachineType.FuncType(paramTy, resultTy)
  def lowerVtable() =
    val sortedtbl = this.vtableCtx.values.toList sortBy {
      case (elemOffset, _) => elemOffset
    }
    sortedtbl map {
      case (elemOffset, tblItems) =>
        val tblFunRefs = tblItems map {
          case (methodName, methodInfo) =>
            val mdef = fDefCtx(methodInfo.fid)
            mdef.name
        }
        (elemOffset, tblFunRefs)
    }

  protected def promoteMethod(cls: ClassInfo, mdef: GODef) =
    val id = mdef.id
    val name = mdef.name
    val isjp = mdef.isjp
    val params = mdef.params
    val resultNum = mdef.resultNum
    val body = mdef.body
    GODef(
      id,
      nameMangling(cls.ident, name),
      false,
      Name("this") +: params,
      resultNum,
      None,
      body
    )

  protected def getRuntimeReprData(classAux: RecordObj): DataString =
    val itable = classAux.getItable
    val size = itable.size
    val traitTbl = itable.flatMap {
      case (traitCls, _) =>
        val (offset, _) = this.vtableCtx(traitImplSym(classAux.getName, traitCls.ident))
        Ls(traitCls.id, offset)
    }
    val rawData = size +: traitTbl
    DataString.fromIntSeq(rawData)

  protected def assembleAddress(xs: Ls[MachineInstr]): Ls[MachineInstr] =
    val got = this.dataCtx.computeOffsets.toMap
    def translateLabel(addr: Address) = addr match
      case LabelAddr(symbol) => ImmOffset(got(symbol)._1)
      case imm: ImmOffset => imm

    xs map {
      instr => instr match
        case LdSym(sym) => LdSym(translateLabel(sym))
        case I32LdOffset(offset) => I32LdOffset(translateLabel(offset))
        case I32StOffset(offset) => I32StOffset(translateLabel(offset))
        case I64LdOffset(offset) => I64LdOffset(translateLabel(offset))
        case I64StOffset(offset) => I64StOffset(translateLabel(offset))
        case x @ _ => x
    }

  protected def preprocess(goprog: GOProgram): Unit =
    val classes = goprog.classes
    val defs = goprog.defs
    this.clsctx = this.clsctx ++ classes.map(cls => (cls.ident -> cls)).toMap
    this.fDefCtx = this.fDefCtx ++ defs.map(fdef => (fdef.id -> fdef)).toMap
    // promote methods
    val methods = classes.toList.flatMap(
      cls => cls.methods.values.map(mdef => promoteMethod(cls, mdef)))
    this.fDefCtx = this.fDefCtx ++ methods.map(mdef => (mdef.id -> mdef))

    // generate vtable and type table
    var vtableSize = 0

    classes.toList.foreach {
      cls =>
        val classAux = RecordObj.from(this.clsctx, cls)
        this.layoutCtx = this.layoutCtx.updated(cls.ident, classAux)
    }
    // create class constructor
    classes.toList.foreach {
      cls =>
        cls.kind match
          case ClsKind => generateCtor(cls)
          case TraitKind =>
    }

    // method types
    classes.toList.foreach {
      cls =>
        val classAux = this.layoutCtx(cls.ident)
        this.typeCtx = this.typeCtx ++ classAux.getVtable.map {
          case(name, methodInfo) =>
            val mdef = this.fDefCtx(methodInfo.fid)
            val methodTy = getFunctionType(mdef)
            val mangleName = typeNameMangling(cls.ident, name)
            mangleName -> methodTy
        }
        this.typeCtx = this.typeCtx ++ classAux.getItable.flatMap {
          case (traitCls, methods) =>
            methods.map {
              case (methodName, methodInfo) =>
                val mdef = this.fDefCtx(methodInfo.fid)
                val methodTy = getFunctionType(mdef)
                val mangleName = typeIfaceNameMangling(cls.ident, traitCls.ident, methodName)
                mangleName -> methodTy
            }
        }
    }
    // global method table
    classes.toList.foreach {
      cls =>
        val classAux = this.layoutCtx(cls.ident)
        // normal virtual method
        if classAux.hasVirtual then
          val elemOffset = vtableSize
          vtableSize += classAux.getVtable.size
          this.vtableCtx = this.vtableCtx.updated(cls.ident, (elemOffset, classAux.getVtable))
        end if
        // merge interface to virtual method ctx
        classAux.getItable foreach { 
          case (traitCls, methods) =>
            val elemOffset = vtableSize
            vtableSize += methods.size
            this.vtableCtx = this.vtableCtx.updated(
              traitImplSym(cls.ident, traitCls.ident),
              (elemOffset, methods.toList)
            )
        }
    }

    val traitDataSegs = classes.toList.foreach {
      cls =>
        cls.kind match
          case ClsKind =>
            val classAux = layoutCtx(cls.ident)
            val rawData = getRuntimeReprData(classAux)
            this.dataCtx.register(itableMangling(cls.ident), rawData)
          // trait it self should not be instantiated
          case TraitKind => ()
    }
    Refcount.registerAllocator(this.dataCtx)

    // register classes names, fields names
    classes.foreach(
      cls =>
        dataCtx.register(cls.ident, DataString.fromString(cls.ident))
        cls.fields.foreach(
          fld =>
            dataCtx.register(fld, DataString.fromString(fld))
        )
    )
    dataCtx.register(builtinUndefined, DataString.fromString(builtinUndefined))

    // register builtin functions
    // CRTC
    builtinCtx.registerFn(builtinDecRef, Refcount.generateDecRef)
    builtinCtx.registerFn(builtinIncRef, Refcount.generateIncRef)
    builtinCtx.registerFn(builtinMalloc, Refcount.generateMalloc)
    builtinCtx.registerFn(builtinFree, Refcount.generateFree)
    builtinCtx.registerFn(builtinReuse, Refcount.generateReuse)
    // interface table search
    builtinCtx.registerFn(builtinItableSearch, SynthFunctions.generateTraitSearch)
    // derived show
    builtinCtx.registerFn(generalShow, SynthFunctions.generateShow(classes))
    classes.foreach {
      cls =>
        builtinCtx.registerFn(objectShow(cls.ident), generateMonoShow(cls))
    }

    val main = GODef(
      -1, // invalid id
      "main",
      false,
      Ls(),
      1, // return only one value (may be pointer)
      None,
      goprog.main
    )
    this.fDefCtx = this.fDefCtx.updated(-1, main)

  protected def translateTrivialExpr(expr: TrivialExpr): Ls[MachineInstr] = expr match
    case Ref(Name(name)) => Ls(GetLocal(name))
    case Literal(lit) => lit match
      case IntLit(value) => Ls(I32Const(value.intValue), I32Const(1), I32Shl, I32Const(1), I32Add, Comment(s"imm $value"))
      case _ => throw WasmBackendError(s"unsupported literal ${lit.toString()}!")
  
  // ref count variant of `translateTrivialExpr`, the name is referenced as a field/argument,
  // thus should increase the ref count.
  protected def translateTrivialExprRefCount(expr: TrivialExpr): Ls[MachineInstr] = expr match
    case Ref(Name(name)) => Ls(
        GetLocal(name),
        Call(builtinIncRef),
        GetLocal(name)
      )
    case Literal(lit) => lit match
      case IntLit(value) => Ls(I32Const(value.intValue), I32Const(1), I32Shl, I32Const(1), I32Add, Comment(s"imm $value"))
      case _ => throw WasmBackendError(s"unsupported literal ${lit.toString()}!")

  protected def translateExpr(expr: GOExpr): Ls[MachineInstr] = expr match
    case s: TrivialExpr => translateTrivialExpr(s)
    case CtorApp(cls, args) =>
      throw WasmBackendError(s"unexpected CtorApp handling for ${expr}")
    case Select(Name(refName), scls, field) =>
      // ad hoc, as select method is built before getClassInfoUniverse
      val cls = clsctx(scls.ident)
      val recordAux = layoutCtx(cls.ident)
      val offset = recordAux.getFieldOffset(field)
      Ls(GetLocal(refName), I32Const(offset), I32Add, I32Load)
    case SelectMember(Name(refName), scls, member, args) =>
      val cls = clsctx(scls.ident)
      val layoutAux = layoutCtx(cls.ident)
      layoutAux.getMemberUniverse(member) match
        case S(ClassMethod(method)) =>
          if !method.isVirtual then
            val mdef = fDefCtx(method.fid)
            val symbolName = mdef.name
            val argsInstr = GetLocal(refName) +: // `this` pointer
                            args.flatMap(arg => translateTrivialExpr(arg))
            argsInstr ++ Ls(Call(symbolName), 
                            Comment(s"call method ${member} of object ${refName}"))
          else
            val (_, vtb) = this.vtableCtx(cls.ident)
            val methodIndex = vtb.indexWhere { _._1 == member}
            val methodTyName = typeNameMangling(cls.ident, method.methodName)
            val pvtboff = RecordObj.getPVtableOffset
            val argsInstr = GetLocal(refName) +: // `this` pointer
                            args.flatMap(arg => translateTrivialExpr(arg))
            argsInstr ++ Ls(GetLocal(refName), I32Const(pvtboff), I32Add, I32Load, Comment(s"class vtable ${cls.ident} offset"), 
                            I32Const(methodIndex), Comment(s"method ${method.methodName} offset"), I32Add,
                            CallIndrect(methodTyName))
        case S(ClassField(offset)) => 
          Ls(GetLocal(refName), I32Const(offset), I32Add, I32Load, 
             Comment(s"select field ${member} of object ${refName}"))
        case S(TraitMethod(traitName, method)) =>
          val traitInfo = clsctx(traitName)
          val builtin = builtinCtx.getBuiltinFn(builtinItableSearch)
          val (_, vtb) = this.vtableCtx(traitImplSym(cls.ident, traitInfo.ident))
          val methodIndex = vtb.indexWhere { _._1 == member }
          val methodTyName = typeIfaceNameMangling(cls.ident, traitInfo.ident, method.methodName)
          val argsInstr = GetLocal(refName) +:
                          args.flatMap(arg => translateTrivialExpr(arg))
          argsInstr ++ Ls(
            // args of searching itable
            GetLocal(refName),
            I32Const(traitInfo.id),
            Call(builtin.name),
            I32Const(methodIndex),
            I32Add,
            CallIndrect(methodTyName)
          )
        case None =>
          throw WasmBackendError(s"invalid member ${member} for class ${cls.ident}")
    
    // BasicOp handles basic arithematic operations,
    // thus our integer representation affects.
    case BasicOp(operator, args) =>
      val opInstr = operator match
        case op if op.equals("+") =>
          // x + y => x + y - 1
          translateTrivialExpr(args(0)) ++ translateTrivialExpr(args(1)) ++ Ls(I32Add, I32Const(-1), I32Add)
        case op if op.equals("-") =>
          // x - y => x - y + 1
          translateTrivialExpr(args(0)) ++ translateTrivialExpr(args(1)) ++ Ls(I32Sub, I32Const(1), I32Add)
        case op if op.equals("*") =>
          // x * y => (x >> 1) * (y - 1) + 1
          translateTrivialExpr(args(0)) ++ Ls(I32Const(1), I32Shr(signed = true)) ++
          translateTrivialExpr(args(1)) ++ Ls(I32Const(-1), I32Add) ++
          Ls(I32Const(1), I32Add)
        case op if op.equals("/") =>
          // x / y => ((x >> 1) / (y << 1) << 1) + 1
          translateTrivialExpr(args(0)) ++ Ls(I32Const(1), I32Shr(signed = true)) ++
          translateTrivialExpr(args(1)) ++ Ls(I32Const(1), I32Shr(signed = true)) ++
          Ls(I32Div, I32Const(1), I32Add)
        case op => throw WasmBackendError(s"unknown operator ${op}")
      opInstr
    // Lambda and App is deprecated
    case expr @ _ => throw WasmBackendError(s"unsupported GOExpr $expr")

  protected def translateNode(node: GONode, initNestBlocks: Int = 0): Ls[MachineInstr] =
    def translateNodeRec(node: GONode, nestedBlocks: Int): Ls[MachineInstr] = node match
      case LetExpr(Name(name), expr, body) =>
        this.locals = this.locals + name
        val exprInstr = allocate(name, expr)
        exprInstr ++ translateNodeRec(body, nestedBlocks)
      case LetJoin(Name(name), params, rhs, body) =>
        // lazy translate
        val jp = JoinPoint(params, rhs, nestedBlocks)
        joinPoints += name -> jp
        // wrap with a block
        val buffer = ListBuffer.from(translateNodeRec(body, nestedBlocks))

        // Wasm requires offer block type, so we give up wrap join point with block temporarily
        buffer += Comment(s"join point ${name}(${params.map{ x => x.toString() }.mkString(",")})" )
        buffer ++= jp()
        buffer.toList

      case LetCall(resultNames, defnRef, args, body) =>
        this.locals = this.locals ++ resultNames.map(resultName => resultName.str)
        // leftmost param in the bottom
        val argsInstr = args flatMap {arg => translateTrivialExpr(arg)}
        val calleeName = defnRef.getName
        val numResultFields = resultNames.size

        // left most result in the bottom
        val bindInstrs = resultNames.reverse flatMap { case Name(name) =>
                            Ls(SetLocal(name))
                          }
        (argsInstr :+ Call(calleeName)) ++ (bindInstrs ++ translateNodeRec(body, nestedBlocks))
      
      // terminal form, may have nested node
      case Case(Name(struct), cases) =>
        val numArms = cases.size 
        // We assume classinfo id of arms is continuous
        val caseIds = cases.map {case (cls, _) => cls.id}
        val minId = caseIds.min
        val maxId = caseIds.max
        if List.range(minId, maxId + 1).toSet equals caseIds.toSet then
          val brTable = caseIds map {_ - minId}
          val dispatchInstrs = Ls(Block(s"case_${struct}", Ls()),
                                  GetLocal(struct),
                                  I32Load,
                                  I32Const(minId), 
                                  I32Sub, 
                                  BrTable(brTable),
                                  Comment("case br table"),
                                  End)
          
          cases.zip(List.range(0, numArms)).foldLeft(dispatchInstrs) {
            case (instrs, ((cls, armNode), index) ) =>
              // depth show number of blocks outside current arm (excluding self)
              val depth = (numArms - index) - 1
              val armInstrs = translateNodeRec(armNode, depth)
            
              // construct a new block for a arm:
              // (block
              //  (block ...)
              //  instr*  
              // )
              Ls(Block(s"match_${struct}_${index}", Ls()), Comment(s"nested blocks: $depth")) ++
                (instrs ++ armInstrs ++ Ls(End, Comment(s"end of match_${struct}_${index}")))
          }
        else 
          throw WasmBackendError("not implemented cases dispatch")
      // terminal forms, no body
      case Jump(defnRef, args) =>
        val joinName = defnRef.getName
        val joinPoint = joinPoints.getOrElse(joinName, 
                                             throw WasmBackendError(s"join point ${joinName} not found"))
        this.locals = this.locals ++ joinPoint.params.map { paramName => paramName.str }
        joinPoint.passArgs(args) :+ Br(nestedBlocks)
        
      case Result(res) =>
        val resRefs = res collect {
          case Ref(Name(name)) => name
        }
        val lexicalScopeDeref = this.locals.toList.filter(x => !resRefs.contains(x)) flatMap {
          x =>
            Ls(GetLocal(x), Call(builtinDecRef))
        }
        val resArgs = res flatMap {expr => translateTrivialExpr(expr)}
        val resultInstrs = resArgs
        nestedBlocks match
          case 0 => resultInstrs
          case labels => resultInstrs :+ Br(nestedBlocks)
    translateNodeRec(node, initNestBlocks)

  // translate GODef to a function.
  // after optimization, some join point will be lifted to a GODef,
  // but we should just treat it as a basic block
  protected def translateGODefs(godef: GODef): Either[MachineFunction, JoinPoint] =
    val name = godef.name
    
    if godef.isjp then
      val jp = JoinPoint(godef.params, godef.body)
      joinPoints += name -> jp
      Right(jp)
    else 
      this.locals = Set()
      val body = translateNode(godef.body)
      val paramsType = godef.params map { case Name(param) => param -> MachineType.defaultNumType}
      // local info collect when codegen
      val localsType = locals.toList map {local => local -> MachineType.defaultNumType}
      val retType = List.fill(godef.resultNum)(MachineType.defaultNumType)
      Left(
        MachineFunction(
          name,
          paramsType,
          localsType,
          retType,
          body)
        )

  // entry of lowering graph ir to wasm module
  def translate(goprog: GOProgram, moduleName: Str): Module =
    preprocess(goprog)
    val functions = fDefCtx.values.toList collect {godef =>
      translateGODefs(godef) match
        case Left(function) => function
    }
    val synthFunctions = builtinCtx.getAllFunctions
    // initialize global memory pointer to end of the data segments memory size
    // this might be replace by allocator and reference couting.
    val globals = List("sp" -> Refcount.roundUp(dataCtx.memorySize))

    val fnIn = functions ++ synthFunctions
    val fnAfterAsm = fnIn.map(fn => fn.updated(assembleAddress(fn.instrs)))

    // name `Module` shadows Module in java.lang
    wasm.Module(
      moduleName,
      globals,
      dataCtx,
      List(), // empty imports
      fnAfterAsm,
      lowerVtable(),
      this.typeCtx
    )

  private def generateCtor(cls: ClassInfo): MachineFunction =
    val ctorName = ctorMangling(cls)
    if builtinCtx.contains(ctorName) then
      builtinCtx(ctorName)
    else 
      val bkupLocals = this.locals
      this.locals = Set()

      val params = cls.fields
      val fields = cls.members
      val parents = cls.parents

      val layoutAux = layoutCtx(cls.ident)
      val instrs = ListBuffer[MachineInstr]()
      // construct parent
      val pGrouped = parents.groupBy {
        case (name, ctor) =>
          val pcls = clsctx(name)
          pcls.kind match
            case ClsKind => 0
            case TraitKind => 1
      }
      // class parents
      val pClass = pGrouped.getOrElse(0, Nil)
      val pc = pClass flatMap {
        case (name, ctor) => 
          val pcls = clsctx(name)
          val poffset = RecordObj.getParentOffet(pcls.ident)
          val pthis = Ls(GetLocal("this"), I32Const(poffset), I32Add)
          val pargs = ctor match
            case S(argNode) => translateNode(argNode)
            case None => Ls()
          val pctorName = nameMangling(name, "constructor")
          val pctor = generateCtor(pcls)
          pthis ++ pargs ++ Ls(Call(pctorName), Comment(f"ctor parent ${pcls.ident}"))
      }
      // trait parents
      val classTraitBtl = itableMangling(cls.ident)
      val itableOffset = RecordObj.getPItableOffset
      val ptc = Ls(
        GetLocal("this"),
        LdSym(LabelAddr(classTraitBtl)),
        I32StOffset(ImmOffset(itableOffset)),
        Comment(s"set itable")
      )
      

      // println(s"class ${cls.ident} virtual: ${layoutAux.hasVirtual}")
      val pvtbc = if layoutAux.hasVirtual then
        val pvtboff = RecordObj.getPVtableOffset
        val vtbOffset = this.vtableCtx(cls.ident)._1
        Ls(
          GetLocal("this"),
          I32Const(pvtboff),
          I32Add,
          I32Const(vtbOffset),
          I32Store,
          Comment(s"vtable pointer class ${cls.ident}")
        )
      else
        Ls()

      // store params
      val paramc = params flatMap { paramName =>
        Ls(
          GetLocal("this"),
          I32Const(layoutAux.getFieldOffset(paramName)),
          I32Add,
          GetLocal(paramName),
          I32Store,
          Comment(s"set param ${paramName}")
        )
      }

      val fc = fields flatMap { case (fieldName, initNode) =>
        Ls(
          GetLocal("this"),
          I32Const(layoutAux.getFieldOffset(fieldName)),
          I32Add, 
        ) ++
        translateNode(initNode) ++
        Ls(
          I32Store,
          Comment(s"set field ${fieldName}")
        )
      }

      val paramsType = ("this" +: params) map { param => param -> MachineType.defaultNumType}
      // local info collect when codegen
      val localsType = locals.toList map {local => local -> MachineType.defaultNumType}
      val retType = Ls()
      val machineF = MachineFunction(
        ctorName,
        paramsType,
        localsType,
        retType,
        (pc ++ ptc ++ pvtbc ++ paramc ++ fc).toList
      )
      builtinCtx.registerFn(ctorName, machineF)
      // TODO: use better way to recover locals
      this.locals = bkupLocals
      machineF

  // We treat Wasm memory as a linear memory pool,
  // each time we allocate a class, we move forward the memory pointer (a global variable)
  private def allocate(bindName: Str, rhs: GOExpr): Ls[MachineInstr] =
    // Debug only
    val ctorInstrs: ListBuffer[MachineInstr] = ListBuffer()
    // store expr values into linear memory
    rhs match
      case CtorApp(scls, args) =>
        val cls = clsctx(scls.ident)
        val recordAux = layoutCtx(cls.ident)
        val objSize = recordAux.size
        val malloc = builtinCtx.getBuiltinFn(builtinMalloc)
        ctorInstrs ++= Ls(I32Const(objSize), Call(malloc.name), SetLocal(bindName))
        ctorInstrs += Comment(s"allocate for ${bindName}")
        // class id
        ctorInstrs ++= Ls(GetLocal(bindName), I32Const(cls.id), I32Store)
        
        val ctorF = generateCtor(cls)
        ctorInstrs ++= Ls(GetLocal(bindName)) // `this` pointer
        ctorInstrs ++= args flatMap {
            arg => translateTrivialExprRefCount(arg)
        }
        ctorInstrs += Call(ctorF.name)

        ctorInstrs += Comment(s"CtorApp ${cls.ident}")
      case _ =>
        ctorInstrs ++= translateExpr(rhs)
        ctorInstrs += SetLocal(bindName)
        ctorInstrs += Comment(s"bind variable ${bindName}")

    ctorInstrs.toList

  def generateMonoShow(cls: ClassInfo) =
    val objParam = "this"
    val layoutAux = this.layoutCtx(cls.ident)

    val logName = Ls(
      LdSym(LabelAddr(cls.ident)),
      I32Const(cls.ident.size),
      Call("log_str"),
      I32Const('('.toInt),
      Call("log_char")
    )

    val logFields = cls.fields.flatMap {
      fld => Ls(
        LdSym(LabelAddr(fld)),
        I32Const(fld.size),
        Call("log_str"),
        I32Const(':'.toInt),
        Call("log_char"),
        I32Const(' '.toInt),
        Call("log_char"),
        GetLocal(objParam),
        I32LdOffset(ImmOffset(layoutAux.getFieldOffset(fld))),
        Call(generalShow),
        I32Const(','.toInt),
        Call("log_char"),
        I32Const(' '.toInt),
        Call("log_char"),
      )
    }

    val epiloge = Ls(
      I32Const(')'.toInt),
      Call("log_char")
    )

    val paramsType = Ls(objParam) map { param => param -> MachineType.defaultNumType}
    // local info collect when codegen
    val localsType = Ls()
    val retType = Ls()
    MachineFunction(
      objectShow(cls.ident),
      paramsType,
      localsType,
      retType,
      logName ++ logFields ++ epiloge
    )


    
end WasmBackend