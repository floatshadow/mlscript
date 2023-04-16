package mlscript.compiler.backend

import mlscript.compiler.backend.Type
import mlscript.utils.lastWords

enum Operand:
  case Const(value: Boolean | Int | Float | String)
  case Var()

  def replace(implicit map: Map[Operand.Var, Operand]): Operand =
    this match
      case Const(_)       => this
      case v: Operand.Var => map.getOrElse(v, this)

  def getType(implicit ctx: Map[Operand.Var, Type]): Type = this match
    case Const(value: Boolean) => Type.Boolean
    case Const(value: Int)     => Type.Int32
    case Const(value: Float)   => Type.Float32
    case Const(value: String)  => Type.OpaquePointer
    case v: Operand.Var        => ctx(v)

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

enum PureValue:
  case Alloc(ty: Type)
  case BinOp(kind: BinOpKind, lhs: Operand, rhs: Operand)
  case Neg(value: Operand)
  case GetField(obj: Operand, field: String)
  case IsType(obj: Operand, ty: Type)
  case Cast(obj: Operand, ty: Type)
  case IsVariant(obj: Operand, variant: String)
  case AsVariant(obj: Operand, variant: String)

  // for optimization usage only, the final codegen should not reach this case
  // this value can be freely removed as it is pure
  case Intrinsic(name: String, args: List[Operand])

  def replace(implicit map: Map[Operand.Var, Operand]): PureValue = this match
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
    case Alloc(ty)               => Seq.empty
    case BinOp(kind, lhs, rhs)   => Seq(lhs, rhs)
    case Neg(value)              => Seq(value)
    case GetField(obj, field)    => Seq(obj)
    case IsType(obj, ty)         => Seq(obj)
    case Cast(obj, ty)           => Seq(obj)
    case IsVariant(obj, variant) => Seq(obj)
    case AsVariant(obj, variant) => Seq(obj)
    case Intrinsic(obj, params)  => params

enum Instruction:
  case Assignment(lhs: Operand.Var, rhs: PureValue)
  case Branch(target: BasicBlock, args: List[Operand])
  case Call(result: Option[Operand.Var], fn: Operand, args: List[Operand])
  case Match(value: Operand, cases: Map[String, BasicBlock])
  case Return(value: Option[Operand])
  case SetField(obj: Operand.Var, field: String, value: Operand)
  case Unreachable

  // for optimization usage only, the final codegen should not reach this case
  case Intrinsic(
      result: Option[Operand.Var],
      name: String,
      args: List[Operand]
  )

class BasicBlock(params: List[Operand.Var], var instructions: List[Instruction])
