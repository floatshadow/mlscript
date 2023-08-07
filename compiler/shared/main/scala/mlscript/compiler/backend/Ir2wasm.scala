package mlscript.compiler.backend
import mlscript.compiler.backend.algorithms._
import mlscript.compiler.backend.Instruction._
import mlscript.compiler.backend.PureValue._
import mlscript.compiler.backend.Operand._
import mlscript.compiler.backend.wasm.WasmInstructions.{
  Return => WasmReturn,
  Unreachable => WasmUnreachable,
  Call => WasmCall,
  _
}
import scala.collection.mutable.ListBuffer
import mlscript.utils.shorthands.*
import mlscript.codegen.CodeGenError
import mlscript.compiler.backend.wasm.CodePrinter
import mlscript.compiler.backend.wasm.LocalsHandler
import mlscript.compiler.backend.wasm.ModulePrinter
import mlscript.compiler.backend.wasm.Function
import mlscript.SimpleTerm
import scala.language.implicitConversions
import scala.collection.mutable.{ListMap, HashMap, LinkedHashMap}

enum ContainingSyntax:
  case IfThenElse
  case LoopHeadedBy(val label: String)
  case BlockFollowedBy(val label: String)

class Ir2wasm {
  import ContainingSyntax._
  val memoryBoundary = 0

  def translate(
      blocks: Ls[(BasicBlock, Map[Operand.Var, Type])],
      moduleName: String,
      imports: List[String],
      classDefMap: Map[Type.TypeName, (LinkedHashMap[String, Type], Int)],
      recordDefMap: Map[Type.Record, Int]
  ) =
    wasm.Module(
      moduleName.replaceAll("/", "_"),
      imports,
      blocks.map((bb, symbolTypeMap) =>
        val isMain = bb.name == "entry"
        Function(if isMain then "main" else bb.name, bb.params, isMain) { lh =>
          val paramsTpe =
            if (bb.params.size == 0)
              Nil
            else
              symbolTypeMap.get(Var(bb.name)) match
                case S(Type.Function(paramTpe, _)) => paramTpe
                case _ => throw CodeGenError(s"unresolved symbol ${bb.name}")
          bb.params
            .zip(paramsTpe)
            .foreach((param, tpe) =>
              lh.register(
                param.name,
                tpe,
                true
              )
            )
          translateFunction(
            bb,
            moduleName,
            imports,
            classDefMap,
            recordDefMap,
            symbolTypeMap,
            lh
          )
        }
      )
    )

  def translateFunction(
      entryBB: BasicBlock,
      moduleName: String,
      imports: List[String],
      classDefMap: Map[Type.TypeName, (LinkedHashMap[String, Type], Int)],
      recordDefMap: Map[Type.Record, Int],
      symbolTypeMap: Map[Operand.Var, Type],
      lh: LocalsHandler
  ): Code =

    var worklist: ListBuffer[BasicBlock] = ListBuffer(entryBB)
    var nodes: ListBuffer[BasicBlock] = ListBuffer()
    var edges: ListBuffer[(BasicBlock, BasicBlock)] = ListBuffer()

    while (worklist.nonEmpty)
      val curr = worklist.head
      worklist = worklist.tail
      if (!nodes.contains(curr))
        edges ++= curr.children.map(child => (curr, child))
        nodes += curr
        worklist ++= curr.children.filterNot(nodes.contains)

    val isLoopHeader = getLoopHeader(edges.toList, nodes.toList)
    val isMergedNode = getMergedNode(edges.toList, nodes.toList)
    val getIndex = reversePostOrder(edges.toList, nodes.toList)
    val getChildern = getImDomChild(edges.toList, nodes.toList)

    def doTree(source: BasicBlock)(implicit
        context: List[ContainingSyntax],
        lh: LocalsHandler
    ): Code =
      val children = getChildern(source)
      val mergedChildren: List[BasicBlock] = children.filter(isMergedNode)
      if (isLoopHeader(source))
        Loop(source.name) <:> nodeWithin(source, mergedChildren)(
          ContainingSyntax.LoopHeadedBy(source.name) +: context,
          lh
        ) <:> End
      else
        nodeWithin(source, mergedChildren)

    def nodeWithin(
        source: BasicBlock,
        children: List[BasicBlock]
    )(implicit
        context: List[ContainingSyntax],
        lh: LocalsHandler
    ): Code = children match
      case node :: rest =>
        Block(
          source.name,
          source.params.map(_ match
            case Var(name) => name
          )
        ) <:> nodeWithin(source, rest)(
          LoopHeadedBy(node.name) +: context,
          lh
        ) <:> End <:> doTree(node)
      case Nil =>
        instrToWasm(source.instructions.toList) <:>
          (source.leaving match
              case S(Branch(target, args)) =>
                doBranch(source, target, args)
              case S(Match(value, cases, default)) =>
                if (cases.size == 1)
                  val (key, (thenBlock, thenArgs)) = cases.head
                  val thenlh = LocalsHandler(lh)
                  (value, key) match
                    case (Operand.Var(op), mlscript.Var(name)) =>
                      // TODO better class shadowing
                      // TODO do it in multi branches case
                      if (classDefMap.keys.exists(Type.TypeName(name) == _))
                        thenlh.register(
                          op,
                          Type.TypeName(name),
                          lh.getIsParam(op)
                        )
                    case _ => ()

                  operandToWasm(value) <:> getLoad(value) <:> operandToWasm(
                    key
                  ) <:> Eq <:>
                    If_void <:> doBranch(source, thenBlock, thenArgs)(
                      context,
                      thenlh
                    ) <:>
                    Else <:> doBranch(source, default._1, default._2) <:> End
                else
                  val casesList = cases.toList
                  Block(default._1.name, Nil) <:>
                    translateCases(
                      value,
                      casesList.map(_._1),
                      casesList.map(_._2),
                      default._1.name
                    ) <:>
                    doTree(default._1)
                    <:> End
              case S(Return(S(Var(op))))           => Code(List(GetLocal(op)))
              case S(Return(S(op: Operand.Const))) => operandToWasm(op)
              case S(Unreachable) => Code(List(WasmUnreachable))
              case _              => Code(Nil)
            // TODO
          )

    def translateCases(
        value: Operand,
        conditions: List[SimpleTerm],
        cases: List[(BasicBlock, List[Operand])],
        default: String
    )(implicit
        context: List[ContainingSyntax],
        lh: LocalsHandler
    ): Code =
      cases match
        case head :: tail =>
          Block(head._1.name, Nil) <:> translateCases(
            value,
            conditions,
            tail,
            default
          ) <:>
            doTree(head._1) <:>
            Br(default) <:>
            End
        case Nil =>
          val comparison: List[Code] =
            conditions.zipWithIndex.map((cond, idx) =>
              operandToWasm(value) <:> getLoad(value) <:>
                operandToWasm(cond) <:>
                Eq <:>
                I32Const(idx + 1) <:>
                Mul
            )
          Block(s"Match_$value", Nil) <:>
            comparison.head <:>
            comparison.tail.map(_ <:> Add) <:>
            BrTable(conditions.length) <:>
            End

    def doBranch(
        source: BasicBlock,
        target: BasicBlock,
        args: List[Operand]
    )(implicit
        context: List[ContainingSyntax],
        lh: LocalsHandler
    ): Code =
      if (getIndex(source) >= getIndex(target) || isMergedNode(target))
        (target.params zip args).map((param, arg) =>
          lh.register(param.name, arg.getType(symbolTypeMap))
          operandToWasm(arg) <:> SetLocal(param.name)
        )
      else
        doTree(target)

    def instrToWasm(instrs: List[Instruction])(implicit
        lh: LocalsHandler
    ): Code = {
      instrs.headOption match
        case S(Assignment(lhs, rhs)) =>
          val head: Code = rhs match
            case Alloc(tpe) =>
              lh.register(lhs.name, tpe)
              GetGlobal(memoryBoundary) <:> SetLocal(
                lhs.name
              ) <:> pureValueToWasm(rhs)
            case Op(op) =>
              lh.register(lhs.name, op.getType(symbolTypeMap))
              pureValueToWasm(rhs) <:> SetLocal(lhs.name)
            case BinOp(_, operand, _) =>
              lh.register(lhs.name, operand.getType(symbolTypeMap))
              pureValueToWasm(rhs) <:> SetLocal(lhs.name)
            case _ => // TODO
              lh.register(lhs.name, Type.Int32)
              pureValueToWasm(rhs) <:> SetLocal(lhs.name)
          head <:> instrToWasm(instrs.tail)
        case S(_: Branch)   => Code(Nil)
        case S(Unreachable) => Code(Nil)
        case S(_: Match)    => Code(Nil)
        case S(_: Return)   => Code(Nil)
        case S(Call(result, Var("log"), args)) =>
          lh.register(result.get.name, Type.Unit)
          val resultCode: Code = I32Const(0)
          val typeCode: Code = args.head.getType(symbolTypeMap) match
            case Type.Unit           => I32Const(0)
            case Type.Boolean        => I32Const(1)
            case Type.Int32          => I32Const(2)
            case Type.Float64        => Code(Nil)
            case Type.OpaquePointer  => I32Const(4)
            case Type.Record(_)      => I32Const(5)
            case Type.Variant(_)     => I32Const(6)
            case Type.Function(_, _) => I32Const(7)
            case Type.TypeName(_)    => I32Const(8)
          val funName = args.head.getType(symbolTypeMap) match
            case Type.Float64 => "logF64"
            case _            => "logI32"
          args.map(operandToWasm) <:>
            typeCode <:>
            WasmCall(funName) <:>
            resultCode <:>
            SetLocal(result.get.name) <:>
            instrToWasm(instrs.tail)
        case S(Call(result, Var(name), args)) =>
          // TODO Currently only log() is call, have to handle the different number/type of result variable
          lh.register(
            result.get.name,
            Type.Unit
          ) // TODO get the type of function result
          val resultCode: Code = symbolTypeMap.get(Var(name)) match
            case S(Type.Function(_, _)) =>
              Code(Nil)
            case S(_) => ???
            case N    => I32Const(0)
          args.map(operandToWasm) <:>
            WasmCall(name) <:>
            resultCode <:>
            SetLocal(result.get.name) <:>
            instrToWasm(instrs.tail)
        case S(SetField(obj, field, value)) =>
          val offset = lh.getType(obj.name) match
            case tpe: Type.TypeName =>
              (classDefMap(tpe)._1.toList.indexWhere(_._1 == field) + 1) * 4
            case Type.Record(recObj) =>
              (recObj.fields.toList.indexWhere(_._1 == field) + 1) * 4
            case _ => ???
          GetLocal(obj.name) <:>
            I32Const(offset) <:>
            Add <:>
            operandToWasm(value) <:>
            Store <:>
            instrToWasm(instrs.tail)
        case S(instr) =>
          ??? // TODO
        case N => Code(Nil)
    }

    def pureValueToWasm(pureValue: PureValue)(implicit
        lh: LocalsHandler
    ): Code = pureValue match
      case Op(op) => operandToWasm(op)
      case Alloc(tpe: Type.TypeName) =>
        val (args, classId) = classDefMap(tpe)
        GetGlobal(memoryBoundary)
          <:> I32Const(classId)
          <:> Store
          <:> GetGlobal(memoryBoundary)
          <:> I32Const((args.size + 1) * 4)
          <:> Add
          <:> SetGlobal(memoryBoundary)
      case Alloc(obj: Type.Record) =>
        GetGlobal(memoryBoundary)
          <:> I32Const(recordDefMap(obj))
          <:> Store
          <:> GetGlobal(memoryBoundary)
          <:> I32Const((obj.impl.fields.size + 1) * 4)
          <:> Add
          <:> SetGlobal(memoryBoundary)
      case Alloc(_) => ???
      case BinOp(kind, lhs, rhs) =>
        val op = (kind, lhs.getType(symbolTypeMap)) match
          case (BinOpKind.Add, Type.Float64) => F64Add
          case (BinOpKind.Add, _)            => Add
          case (BinOpKind.Sub, Type.Float64) => F64Sub
          case (BinOpKind.Sub, _)            => Sub
          case (BinOpKind.Mul, Type.Float64) => F64Mul
          case (BinOpKind.Mul, _)            => Mul
          case (BinOpKind.Div, Type.Float64) => F64Div
          case (BinOpKind.Div, _)            => Div
          case (BinOpKind.Rem, _)            => Rem
          case (BinOpKind.And, Type.Float64) => F64And
          case (BinOpKind.And, _)            => And
          case (BinOpKind.Or, Type.Float64)  => F64Or
          case (BinOpKind.Or, _)             => Or
          case (BinOpKind.Xor, Type.Float64) => ???
          case (BinOpKind.Xor, _)            => ???
          case (BinOpKind.Eq, Type.Float64)  => F64Eq
          case (BinOpKind.Eq, _)             => Eq
          case (BinOpKind.Ne, Type.Float64)  => ???
          case (BinOpKind.Ne, _)             => ???
          case (BinOpKind.Lt, Type.Float64)  => F64Lt_s
          case (BinOpKind.Lt, _)             => Lt_s
          case (BinOpKind.Le, Type.Float64)  => F64Le_s
          case (BinOpKind.Le, _)             => Le_s
        operandToWasm(lhs) <:> operandToWasm(rhs) <:> op
      case Neg(value) => ???
      case GetField(obj, field) =>
        val (name, tpe) = obj match
          case Var(str) => (str, lh.getType(str))
          case _        => ??? // TODO record type
        val offset = tpe match
          case tpe: Type.TypeName =>
            val (args, classId) = classDefMap(tpe)
            (args.map(_._1).toList.indexOf(field) + 1) * 4
          case Type.Record(recObj) =>
            (recObj.fields.map(_._1).toList.indexOf(field) + 1) * 4
          case _ => 4
        GetLocal(name) <:> I32Const(offset) <:> Add <:> Load
      case IsType(obj, ty)         => ???
      case Cast(obj, ty)           => ???
      case IsVariant(obj, variant) => ???
      case AsVariant(obj, variant) => ???

    implicit def simpleTerm2Operand(term: SimpleTerm): Operand = term match
      case mlscript.IntLit(v)    => Operand.Const(v.toInt)
      case mlscript.DecLit(v)    => Operand.Const(v.toFloat)
      case mlscript.StrLit(v)    => Operand.Const(v)
      case mlscript.UnitLit(v)   => Operand.Unit
      case mlscript.Var("true")  => Operand.Const(true)
      case mlscript.Var("false") => Operand.Const(false)
      case mlscript.Var(name)    => Operand.Var(name)

    def operandToWasm(op: Operand): Code = op match
      case Const(value: Boolean) =>
        I32Const(if (value) 1 else 0)
      case Const(value: Int) =>
        I32Const(value)
      case Const(value: Float) =>
        F64Const(value)
      case Const(value: String) =>
        mkString(value)
      case Unit =>
        I32Const(0)
      case Var(name) =>
        if (classDefMap.keys.exists(_.name == name))
          I32Const(classDefMap(Type.TypeName(name))._2)
        else
          GetLocal(name)

    def getLoad(op: Operand)(implicit lh: LocalsHandler): Code = op match
      case Var(name) =>
        lh.getType(name) match
          case Type.TypeName(_) => Load
          case _                => Code(Nil)
      case _ => Code(Nil)

    def mkString(s: String): Code =
      val size = s.length
      val padding = 4 - size % 4

      val completeS = s + 0.toChar.toString * padding

      val setChars: Code =
        for ((c, ind) <- completeS.zipWithIndex.toList) yield {
          GetGlobal(memoryBoundary) <:> I32Const(ind) <:> Add <:>
            I32Const(c.toInt) <:> Store8
        }

      val setMemory: Code =
        GetGlobal(memoryBoundary) <:> GetGlobal(memoryBoundary) <:> I32Const(
          size + padding
        ) <:> Add <:>
          SetGlobal(memoryBoundary)

      setChars <:> setMemory

    doTree(entryBB)(Nil, lh)
}
