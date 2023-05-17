package mlscript.compiler.backend

import mlscript.compiler.backend.Type
import mlscript.utils.lastWords
import scala.collection.mutable.ListBuffer
import scala.collection.immutable
import mlscript.utils.shorthands.*

enum Operand:
  case Const(val value: Boolean | Int | Float | String)
  case Unit
  case Var(val name: String)

  def replace(implicit map: Map[Operand.Var, Operand]): Operand =
    this match
      case v: Operand.Var => map.getOrElse(v, this)
      case _              => this

  def getType(implicit ctx: Map[Operand.Var, Type]): Type = this match
    case Const(value: Boolean) => Type.Boolean
    case Const(value: Int)     => Type.Int32
    case Const(value: Float)   => Type.Float32
    case Const(value: String)  => Type.OpaquePointer
    case Unit                  => Type.Unit
    case v: Operand.Var        => ctx(v)

  override def toString(): String = this match
    case Operand.Const(value: String) => return s"""$value"""
    case Operand.Const(value)         => return value.toString()
    case Operand.Unit                 => return "()"
    case Operand.Var(name)            => return name

enum BinOpKind:
  case Add
  case Sub
  case Mul
  case Div
  case Rem
  case And
  case Or
  case Xor
  case Eq
  case Ne
  case Lt
  case Le

object BinOpKind:
  private val map = immutable.HashMap[Str, BinOpKind](
    "+" -> Add,
    "-" -> Sub,
    "*" -> Mul,
    "/" -> Div,
    "%" -> Rem,
    "&" -> And,
    "|" -> Or,
    "&&" -> And,
    "||" -> Or,
    "^" -> Xor,
    "==" -> Eq,
    "!=" -> Ne,
    "<" -> Lt,
    "<=" -> Le
  )
  def getBinOp(op: Str): Option[BinOpKind] = map.get(op)

enum PureValue:
  case Op(val op: Operand)
  case Alloc(val ty: Type)
  case BinOp(val kind: BinOpKind, val lhs: Operand, val rhs: Operand)
  case Neg(val value: Operand)
  case GetField(val obj: Operand, val field: String)
  case IsType(val obj: Operand, val ty: Type)
  case Cast(val obj: Operand, val ty: Type)
  case IsVariant(val obj: Operand, val variant: String)
  case AsVariant(val obj: Operand, val variant: String)

  // for optimization usage only, the final codegen should not reach this case
  // this value can be freely removed as it is pure
  case Intrinsic(val name: String, val args: List[Operand])

  def replace(implicit map: Map[Operand.Var, Operand]): PureValue = this match
    case Op(op)                  => Op(op.replace)
    case Alloc(ty)               => Alloc(ty)
    case BinOp(kind, lhs, rhs)   => BinOp(kind, lhs.replace, rhs.replace)
    case Neg(value)              => Neg(value.replace)
    case GetField(obj, field)    => GetField(obj.replace, field)
    case IsType(obj, ty)         => IsType(obj.replace, ty)
    case Cast(obj, ty)           => Cast(obj.replace, ty)
    case IsVariant(obj, variant) => IsVariant(obj.replace, variant)
    case AsVariant(obj, variant) => AsVariant(obj.replace, variant)
    case Intrinsic(name, params) => Intrinsic(name, params.map(_.replace))

  def operands: Seq[Operand] = this match
    case Op(op)                  => Seq(op)
    case Alloc(ty)               => Seq.empty
    case BinOp(kind, lhs, rhs)   => Seq(lhs, rhs)
    case Neg(value)              => Seq(value)
    case GetField(obj, field)    => Seq(obj)
    case IsType(obj, ty)         => Seq(obj)
    case Cast(obj, ty)           => Seq(obj)
    case IsVariant(obj, variant) => Seq(obj)
    case AsVariant(obj, variant) => Seq(obj)
    case Intrinsic(obj, params)  => params

  override def toString(): String = this match
    case Op(op)                  => s"${op}"
    case Alloc(ty)               => s"alloc ${ty}"
    case BinOp(kind, lhs, rhs)   => s"${kind} ${lhs}, ${rhs}"
    case Neg(value)              => s"neg ${value}"
    case GetField(obj, field)    => s"getfield ${obj}, ${field}"
    case IsType(obj, ty)         => s"istype ${obj}, ${ty}"
    case Cast(obj, ty)           => s"cast ${obj}, ${ty}"
    case IsVariant(obj, variant) => s"isvariant ${obj}, ${variant}"
    case AsVariant(obj, variant) => s"asvariant ${obj}, ${variant}"
    case Intrinsic(name, params) =>
      s"intrinsic ${name} (${params.mkString(", ")})"

enum Instruction:
  case Assignment(val lhs: Operand.Var, val rhs: PureValue)
  case Branch(val target: BasicBlock, val args: List[Operand])
  case Call(
      val result: Option[Operand.Var],
      val fn: Operand,
      args: List[Operand]
  )
  case Match(
      val value: Operand,
      val cases: Map[String, (BasicBlock, List[Operand])]
  )
  case Return(val value: Option[Operand])
  case SetField(val obj: Operand.Var, val field: String, val value: Operand)
  case Unreachable

  // for optimization usage only, the final codegen should not reach this case
  case Intrinsic(
      val result: Option[Operand.Var],
      val name: String,
      val args: List[Operand]
  )

class BasicBlock(
    val name: String,
    val params: List[Operand.Var],
    var instructions: ListBuffer[Instruction]
):
  def printIR =
    var visited: Map[String, BasicBlock] = Map(name -> this)
    var toPrint = List(this)
    def visitBlock(block: BasicBlock) =
      val old = visited.get(block.name)
      if old.isEmpty then
        visited += (block.name -> block)
        toPrint = block :: toPrint
      else if old != Some(block) then lastWords(s"invalid ir")
    def printInstruction(inst: Instruction): String = inst match
      case Instruction.Assignment(lhs, rhs) => s"  ${lhs} = ${rhs}"
      case Instruction.Branch(target, args) =>
        visitBlock(target)
        s"  br ${target.name} (${args.mkString(", ")})"
      case Instruction.Call(Some(result), fn, args) =>
        s"  $result = call ${fn} (${args.mkString(", ")})"
      case Instruction.Call(None, fn, args) =>
        s"  call ${fn} (${args.mkString(", ")})"
      case Instruction.Match(value, cases) =>
        val casesStr = cases
          .map((name, value) =>
            s"    ${name} -> ${value._1.name} (${value._2.mkString(", ")})"
          )
          .mkString("\n")
        s"  match ${value}\n${casesStr}"
      case Instruction.Return(value) =>
        "  return" + value.map(v => s" $v").getOrElse("")
      case Instruction.SetField(obj, field, value) =>
        s"  setfield ${obj}.${field} = ${value}"
      case Instruction.Unreachable => s"  unreachable"
      case Instruction.Intrinsic(Some(result), name, args) =>
        s"  $result = intrinsic ${name} (${args.mkString(", ")})"
      case Instruction.Intrinsic(None, name, args) =>
        s"  intrinsic ${name} (${args.mkString(", ")})"

    var buffer = new StringBuilder
    while toPrint.nonEmpty do
      val block = toPrint.head
      toPrint = toPrint.tail
      for (str <- block.instructions.map(printInstruction))
        buffer ++= str
        buffer += '\n'
    buffer.toString()
