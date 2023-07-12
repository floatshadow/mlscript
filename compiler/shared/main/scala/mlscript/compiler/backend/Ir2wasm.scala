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
import mlscript.TypeName

enum ContainingSyntax:
  case IfThenElse
  case LoopHeadedBy(val label: String)
  case BlockFollowedBy(val label: String)

class Ir2wasm {
  import ContainingSyntax._
  val memoryBoundary = 0

  def translate(
      entryBB: BasicBlock,
      moduleName: String,
      imports: List[String],
      symbolTypeMap: Map[String, (Type, Int)]
  ) =

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
                  operandToWasm(value) <:> getLoad(value) <:> operandToWasm(
                    key
                  ) <:> Eq <:>
                    If_void <:> doBranch(source, thenBlock, thenArgs) <:>
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
              case S(Return(value)) => Code(Nil) // TODO
              case S(Unreachable)   => Code(List(WasmUnreachable))
              case _ =>
                Code(Nil)
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
          lh.register(param.name, Type.Int32) // TODO
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
            case Op(Var(op)) =>
              lh.register(lhs.name, lh.getType(op))
              pureValueToWasm(rhs) <:> SetLocal(lhs.name)
            case _ => // TODO
              lh.register(lhs.name, Type.Int32)
              pureValueToWasm(rhs) <:> SetLocal(lhs.name)
          head <:> instrToWasm(instrs.tail)
        case S(_: Branch)                     => Code(Nil)
        case S(Unreachable)                   => Code(Nil)
        case S(_: Match)                      => Code(Nil)
        case S(_: Return)                     => Code(Nil)
        case S(Call(result, Var(name), args)) =>
          // TODO Currently only log() is call, have to handle the different number/type of result variable
          lh.register(result.get.name, Type.Unit)
          args.map(operandToWasm) <:>
            WasmCall(name) <:>
            I32Const(0) <:>
            SetLocal(result.get.name) <:>
            instrToWasm(instrs.tail)
        case S(SetField(obj, field, value)) =>
          val tpe = lh.getType(obj.name)
          val offset = tpe match
            case Type.TypeName(name, _) => (symbolTypeMap(name)._2 + 1) * 4
            case _                      => ???
          GetLocal(obj.name) <:>
            I32Const(4) <:>
            Add <:>
            operandToWasm(value) <:>
            Store <:>
            instrToWasm(instrs.tail)
        case S(instr) =>
          Comment(s"Not implemented instruction: $instr")
        case N => Code(Nil)
    }

    def pureValueToWasm(pureValue: PureValue)(implicit
        lh: LocalsHandler
    ): Code = pureValue match
      case Op(op) => operandToWasm(op)
      case Alloc(Type.TypeName(name, args)) =>
        GetGlobal(memoryBoundary)
          <:> I32Const(symbolTypeMap(name)._2)
          <:> Store
          <:> GetGlobal(memoryBoundary)
          <:> I32Const((args.length + 1) * 4)
          <:> Add
          <:> SetGlobal(memoryBoundary)
      case Alloc(_) => ???
      case BinOp(kind, lhs, rhs) =>
        val op = kind match
          case BinOpKind.Add => Add
          case BinOpKind.Sub => Sub
          case BinOpKind.Mul => Mul
          case BinOpKind.Div => Div
          case BinOpKind.Rem => Rem
          case BinOpKind.And => And
          case BinOpKind.Or  => Or
          case BinOpKind.Xor => ???
          case BinOpKind.Eq  => Eq
          case BinOpKind.Ne  => ???
          case BinOpKind.Lt  => Lt_s
          case BinOpKind.Le  => Le_s
        operandToWasm(lhs) <:> operandToWasm(rhs) <:> op
      case Neg(value) => ???
      case GetField(obj, field) =>
        val (name, tpe) = obj match
          case Var(str) => (str, lh.getType(str))
          case _        => ??? // TODO record type
        val offset = tpe match
          case Type.TypeName(_, args) => (args.map(_._1).indexOf(field) + 1) * 4
          case _                      => ??? // TODO record type
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
        F32Const(value)
      case Const(value: String) =>
        mkString(value)
      case Unit =>
        I32Const(0)
      case Var(name) =>
        if (symbolTypeMap.keys.exists(_ == name))
          I32Const(symbolTypeMap(name)._2)
        else
          GetLocal(name)

    def getLoad(op: Operand)(implicit lh: LocalsHandler): Code = op match
      case Var(name) =>
        lh.getType(name) match
          case Type.TypeName(_, _) => Load
          case _                   => Code(Nil)
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

    // TODO:
    wasm.Module(
      moduleName.replaceAll("/", "_"),
      imports,
      1,
      List(Function("main", 0, true) { lh => doTree(entryBB)(Nil, lh) })
    )
}
