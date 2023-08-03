package mlscript.compiler.backend
import mlscript.compiler.backend.algorithms._
import mlscript.compiler.backend.BasicBlock
import mlscript.compiler.backend.PureValue
import scala.collection.mutable.Map as MutMap

final case class VerificationError(message: String) extends Exception(message)

object Verifier {

  def apply(
      start: BasicBlock
  )(implicit
      ctx: MutMap[Operand.Var, Type] = MutMap.empty[Operand.Var, Type]
  ) = {
    val (edges, nodes, defs, uses) = getCFG(start)(ctx, Set(start))
    val imDominator: Map[BasicBlock, Option[BasicBlock]] =
      getImmediateDominator(edges.toList, nodes.toList)
    checkDefUse(start, imDominator, defs, uses)
  }

  def getCFG(
      block: BasicBlock
  )(implicit ctx: MutMap[Operand.Var, Type], checkedBlock: Set[BasicBlock]): (
      Set[(BasicBlock, BasicBlock)],
      Set[BasicBlock],
      Map[BasicBlock, Set[Operand]],
      Map[BasicBlock, Set[Operand]]
  ) = {
    var edges: Set[(BasicBlock, BasicBlock)] = Set()
    var defs: MutMap[BasicBlock, Set[Operand]] = MutMap(block -> Set())
    var uses: MutMap[BasicBlock, Set[Operand]] = MutMap(block -> Set())
    var workList: List[BasicBlock] = List(block)
    var visited: Set[BasicBlock] = Set()

    while (workList.nonEmpty) {
      val curr = workList.head
      workList = workList.tail
      if (!visited.contains(curr)) {
        val (succs, moreDefs, moreUses) = verifyBasicBlock(
          curr.instructions.toList
        )
        edges ++= succs.map(succ => (curr, succ))
        defs += curr -> moreDefs
        uses += curr -> moreUses
        visited += curr
        workList ++= succs.filterNot(visited.contains)
      }
    }
    (edges, visited, defs.toMap, uses.toMap)
  }

  def verifyBasicBlock(instrs: List[Instruction])(implicit
      ctx: MutMap[Operand.Var, Type] = MutMap.empty
  ): (List[BasicBlock], Set[Operand], Set[Operand]) = {
    var terminated: Boolean = false
    var succs: List[BasicBlock] = List()
    var defs: Set[Operand] = Set()
    var uses: Set[Operand] = Set()
    var workList: List[Instruction] = instrs
    while (workList.nonEmpty && !terminated)
      val instr = workList.head
      workList = workList.tail
      instr match
        case Instruction.Assignment(lhs, rhs) =>
          if (defs contains lhs)
            throw VerificationError(s"Variable $lhs has been defined")
          rhs.operands.foreach(operand =>
            if (lhs == operand) {
              throw VerificationError(
                s"Variable $lhs cannot be used in its definition"
              )
            }
            try { operand.getType(ctx.toMap) }
            catch
              (
                  e =>
                    throw VerificationError(
                      s"${e.getMessage()} in instruction $instr"
                    )
              )
          )
          val (moreCtx, moreUses) = verifyPureValue(rhs, ctx)
          defs = defs | Set(lhs)
          uses = uses | moreUses
          ctx += lhs -> moreCtx
        case Instruction.Branch(target, args) =>
          succs = List(target)
          uses = uses | args.toSet
          terminated = true
        case Instruction.Call(result, fn, args) =>
          val fnType = fn.getType(ctx.toMap) match {
            case f: Type.Function => f
            case _ => throw VerificationError(s"$fn is not a function type")
          }
          if (args.length != fnType.args.length) {
            throw VerificationError(
              s"Function $fn incorrect number of arguments"
            )
          }
          (args.map(_.getType(ctx.toMap)) zip fnType.args).foreach {
            case (found, expected) if found != expected =>
              throw VerificationError(
                s"Function $fn argument type expected = $expected, found = $found"
              )
            case _ => ()
          }
          (result, fnType.ret) match
            case (Some(operand), tpe) =>
              defs = defs | Set(operand)
              ctx += operand -> tpe
            case _ => ()
          uses = uses | (args ++ List(fn)).toSet
        case Instruction.Match(value, cases, default) =>
          succs = cases.values.map(_._1).toList :+ default._1
          uses = uses | Set(value) | default._2.toSet | cases.values
            .map(_._2.toSet)
            .reduce((set1, set2) => set1 | set2)
          terminated = true
        case Instruction.Return(value) =>
          value match
            case Some(x) => uses = uses | Set(x)
            case None    => ()
          terminated = true
        case Instruction.SetField(obj, field, value) =>
          val ty = obj.getType(ctx.toMap)
          ty match
            case Type.Record(impl) =>
              impl(field) match
                case Some(expected) =>
                  val found = value.getType(ctx.toMap)
                  if (expected != found)
                    throw VerificationError(
                      s"$obj.$field expected type:$expected Found:$found"
                    )
                    uses = uses | Set(obj)
                case _ =>
                  throw VerificationError(s"$ty does not contain field $field")
            case _ =>
              throw VerificationError(s"SetField is not allowed in type $ty")
        case Instruction.Unreachable =>
          terminated = true
    if (terminated)
      (succs, defs, uses)
    else
      throw VerificationError(
        s"Unterminated basic block. The block ends with instruction: ${instrs.last}"
      )
  }

  def verifyPureValue(
      value: PureValue,
      ctx0: MutMap[Operand.Var, Type]
  ): (Type, Set[Operand]) = {
    val ctx = ctx0.toMap
    value match
      case PureValue.Op(op) =>
        (op.getType(ctx), Set(op))
      case PureValue.Alloc(ty) =>
        (ty, Set.empty)
      case PureValue.BinOp(kind, lhs, rhs) =>
        val lhst = lhs.getType(ctx)
        val rhst = rhs.getType(ctx)
        if (lhst != rhst)
          throw VerificationError(
            s"Inconsistent type in binary operator found: ${kind} ${lhst} ${rhst}"
          )
        (kind, lhs.getType(ctx)) match
          case (
                BinOpKind.Add | BinOpKind.Sub | BinOpKind.Mul | BinOpKind.Div |
                BinOpKind.Rem,
                Type.Int32 | Type.Float64
              ) =>
            (lhst, Set(lhs, rhs))
          case (
                BinOpKind.And | BinOpKind.Or | BinOpKind.Xor,
                Type.Int32 | Type.Boolean
              ) =>
            (lhst, Set(lhs, rhs))
          case (
                BinOpKind.Eq | BinOpKind.Ne,
                Type.Int32 | Type.Float64 | Type.Boolean
              ) =>
            (Type.Boolean, Set(lhs, rhs))
          case (BinOpKind.Lt | BinOpKind.Le, Type.Int32 | Type.Float64) =>
            (Type.Boolean, Set(lhs, rhs))
          case (_, _) =>
            throw VerificationError(
              s"Binary operator does not accept type found: ${kind} ${lhst} ${rhst}"
            )
      case PureValue.Neg(value) =>
        val valtype = value.getType(ctx)
        if (
          valtype != Type.Boolean && valtype != Type.Int32 && valtype != Type.Float64
        ) throw VerificationError(s"Negation does not accept type ${valtype}")
        else (valtype, Set(value))
      case PureValue.GetField(obj, field) =>
        val ty = obj.getType(ctx)
        ty match
          case Type.Record(impl) =>
            impl(field) match
              case Some(ty) => (ty, Set(obj))
              case _ =>
                throw VerificationError(s"$ty does not contain field $field")
          case _ =>
            throw VerificationError(s"GetField is not allowed in type $ty")
      case PureValue.IsType(obj, ty) => (Type.Boolean, Set(obj))
      case PureValue.Cast(obj, ty)   => (ty, Set(obj))
      case PureValue.IsVariant(obj, variant) =>
        val ty = obj.getType(ctx)
        ty match
          case Type.Variant(impl) => (Type.Boolean, Set(obj))
          case _ =>
            throw VerificationError(s"IsVariant is not allowed in type $ty")
      case PureValue.AsVariant(obj, variant) =>
        val ty = obj.getType(ctx)
        ty match
          case Type.Variant(impl) =>
            impl(variant) match
              case Some(Some(ty)) => (ty, Set(obj))
              case _ =>
                throw VerificationError(
                  s"$ty does not contain variant $variant"
                )
          case _ =>
            throw VerificationError(s"AsVariant is not allowed in type $ty")
  }

  def checkDefUse(
      start: BasicBlock,
      imDominator: Map[BasicBlock, Option[BasicBlock]],
      defs: Map[BasicBlock, Set[Operand]],
      uses: Map[BasicBlock, Set[Operand]]
  ) = {

    val reverseImDom: Map[BasicBlock, List[BasicBlock]] =
      imDominator.foldLeft(Map.empty[BasicBlock, List[BasicBlock]]) {
        case (acc, (k, Some(v))) => acc + (v -> (k :: acc.getOrElse(v, Nil)))
        case (acc, _)            => acc
      }

    var workList: List[BasicBlock] = List(start)
    var defAcc: MutMap[BasicBlock, Set[Operand]] = MutMap(start -> defs(start))

    while (workList.nonEmpty) {
      val curr = workList.head
      workList = workList.tail

      if (reverseImDom.exists { case (block, nexts) => block == curr }) {
        val nexts = reverseImDom(curr)
        workList = workList ++ nexts
        nexts.foreach(next => defAcc += next -> (defAcc(curr) ++ defs(next)))
      }

      defs(curr).foreach(operand =>
        if (imDominator(curr).exists(imDom => defs(imDom) contains operand))
          throw VerificationError(s"Variable $operand has been defined")
      )

      uses(curr).foreach(operand =>
        operand match
          case Operand.Var(x) =>
            if (
              !curr.params.contains(operand) && !defAcc(curr).contains(operand)
            )
              throw VerificationError(s"Variable $x is undefined")
          case _ => ()
      )
    }
  }
}
