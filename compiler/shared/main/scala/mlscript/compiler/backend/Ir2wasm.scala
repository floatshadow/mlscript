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
import mlscript.compiler.backend.wasm.LocalsHandler

enum ContainingSyntax:
  case IfThenElse
  case LoopHeadedBy(val label: String)
  case BlockFollowedBy(val label: String)

class Ir2wasm {
  import ContainingSyntax._

  def translate(
      entryBB: BasicBlock
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

    def doTree(source: BasicBlock, children: List[BasicBlock])(implicit
        context: List[ContainingSyntax]
    ): Code =
      val mergedChildren: List[BasicBlock] = children.filter(isMergedNode)
      if (isLoopHeader(source))
        Loop(None) <:> nodeWithin(source, mergedChildren)(
          ContainingSyntax.LoopHeadedBy(source.name) +: context
        ) <:> End
      else
        nodeWithin(source, mergedChildren)

    def nodeWithin(source: BasicBlock, children: List[BasicBlock])(implicit
        context: List[ContainingSyntax]
    ): Code =
      children match
        case node :: rest =>
          Block(None) <:> nodeWithin(source, rest)(
            LoopHeadedBy(node.name) +: context
          ) <:> End <:> doTree(node, node.children)
        case Nil =>
          instrToWasm(source.instructions.toList)(Map(), LocalsHandler(0)) <:>
            (source.leaving match
              case S(Branch(target, args))         => doBranch(source, target)
              case S(Match(value, cases, default)) => ??? // TODO
              case S(Return(value))                => ??? // TODO
              case S(Unreachable) => Code(List(WasmUnreachable))
              case _              => Code(List(Comment("TODO")))
            )

    def doBranch(source: BasicBlock, target: BasicBlock)(implicit
        context: List[ContainingSyntax]
    ): Code =

      def breakIndex(label: String, ctx: List[ContainingSyntax]): Int =
        val index = Math.max(
          ctx.indexOf(LoopHeadedBy(label)),
          ctx.indexOf(BlockFollowedBy(label))
        )
        if (index == -1)
          throw CodeGenError(s"$label is not in the context")
        else
          index

      if (getIndex(source) >= getIndex(target) || isMergedNode(target))
        Br(breakIndex(target.name, context))
      else
        doTree(target, target.children)

    def instrToWasm(
        instrs: List[Instruction]
    )(implicit locals: Map[String, Int], lh: LocalsHandler): Code = {
      val codes: List[Code] = instrs.map(_ match
        case Assignment(lhs, rhs) =>
          val id = lh.getFreshLocal()
          val moreLocals = locals + (lhs.name -> id)
          pureValueToWasm(rhs) <:> SetLocal(id) <:>
            instrToWasm(instrs.tail)(moreLocals, lh)
        case _ => Comment("TODO")
      )
      codes
    }

    def pureValueToWasm(
        pureValue: PureValue
    )(implicit locals: Map[String, Int], lh: LocalsHandler): Code = {
      pureValue match
        case Op(op)                  => operandToWasm(op)
        case Alloc(ty)               => ??? // TODO
        case BinOp(kind, lhs, rhs)   => ??? // TODO
        case Neg(value)              => ??? // TODO
        case GetField(obj, field)    => ??? // TODO
        case IsType(obj, ty)         => ??? // TODO
        case Cast(obj, ty)           => ??? // TODO
        case IsVariant(obj, variant) => ??? // TODO
        case AsVariant(obj, variant) => ??? // TODO
    }

    def operandToWasm(
        op: Operand
    )(implicit locals: Map[String, Int], lh: LocalsHandler): Code =
      op match
        case Const(value: Boolean) =>
          I32Const(if (value) 1 else 0)
        case Const(value: Int) =>
          I32Const(value)
        case Const(value: Float) =>
          F32Const(value)
        case Const(value: String) => ??? // TODO
        case Unit =>
          I32Const(0)
        case Var(name) =>
          locals.get(name) match
            case Some(id) => GetLocal(id)
            case None => throw CodeGenError(s"ID not found for variable $name")

    doTree(entryBB, entryBB.children)(Nil)
}
