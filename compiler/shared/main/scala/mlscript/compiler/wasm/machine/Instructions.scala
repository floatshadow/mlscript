package mlscript.compiler.wasm


import mlscript.utils.*
import mlscript.utils.shorthands.*

sealed abstract class Address:

  override def toString(): String = this match
    case LabelAddr(symbol) => s"$$$symbol"
    case ImmOffset(offset) => s"$offset"

// Pseudo label offset, would fail in assembler
final case class LabelAddr(symbol: Str) extends Address
final case class ImmOffset(offset: Int) extends Address


// A subset of instructions defined by the WASM standard
// TODO: linear stack machine instruction may be hard to read,
// We hope that we can use s-expression like structure as a better representation
enum MachineInstr:
  // Load an int32 constant to the stack
  case I32Const(value: Int)
  case I64Const(value: Int)
  case F64Const(value: Float)

  // Numeric/logical instructions
  case I32Add
  case I32Sub
  case I32Mul
  case I32Div
  case I32Rem
  case I32And
  case I32Or
  case I32Eqz
  case I32Lt(signed: Boolean = false)
  case I32Le(signed: Boolean = false)
  case I32Gt(signed: Boolean = false)
  case I32Ge(signed: Boolean = false)
  case I32Eq
  case I32Shl
  case I32Shr(signed: Boolean = false)
  case I64Add
  case I64Sub
  case I64Mul
  case I64Div
  case I64Rem
  case I64And
  case I64Or
  case I64Eqz
  case I64Lt(signed: Boolean = false)
  case I64Le(signed: Boolean = false)
  case I64Gt(signed: Boolean = false)
  case I64Ge(signed: Boolean = false)
  case I64Eq
  case I64Shl
  case I64Shr(signed: Boolean = false)
  case Drop // Drops the top value of the stack
  case F64Add
  case F64Sub
  case F64Mul
  case F64Div
  case F64And
  case F64Or
  case F64Eqz
  // Return 1 if operand is 0, 0 otherwise
  case F64Lt_s // Signed less-than
  case F64Le_s // Signed less-equals
  case F64Eq

  // Control instructions
  case If_void
  // Marks the beginning of an if-block (with implicit 'then').
  case If_i32
  // Marks the beginning of an if-block (with implicit 'then'). Must leave an i32 on the stack
  case Else
  // Marks the end of the implicit 'then' of an if-block
  case End
  // Marks the end of an if-then-else or block
  case Loop(label: Str)
  // A block of instructions with a label at the beginning
  case Block(label: Str, results: Ls[Type])
  // A block of instructions with a label at the end
  case Br(label: Int)
  case BrIf(label: Int)
  // Jump to "label", which MUST be the label of an enclosing structure
  case BrTable(labels: Ls[Int])
  case Call(name: Str)
  case CallIndrect(typeName: Str)
  case Return
  case Unreachable // Always fails the program

  // Locals (parameters, local variables)
  case GetLocal(name: Str)
  case SetLocal(name: Str)
  case TeeLocal(name: Str)

  // Global variables
  case GetGlobal(index: Int)
  case SetGlobal(index: Int)

  // Memory
  // Stores an i32/i64 to memory. Expects memory address, then stored value as operands
  case I32Store
  case I64Store
  // Loads an i32/i64 to memory. Expects memory address as operand
  case I32Load
  case I64Load
  // Stores a single byte to memory (the least significant byte of the operand)
  // Operands expected are like Store
  case I32Store8
  // Load a byte from memory, then zero-extend it to fill an i32
  case I32Load8_u

  // Comment
  case Comment(msg: String)
  // Pseudo instructions
  case LdSym(sym: Address) 
  // Push the address of a symbol (usually name of a data segment) into the stack
  case I32LdOffset(offset: Address)
  case I32StOffset(offset: Address)
  case I64LdOffset(offset: Address)
  case I64StOffset(offset: Address)
  // inn.store/load {offset u32, align u32}

  override def toString() : String = show

  def show : String = toDocument.print
  
  def toDocument : Document = this match 
    case I32Const(value)        => s"i32.const $value" |> raw
      case I64Const(value)      => s"i64.const $value" |> raw
      case F64Const(value)      => s"f64.const $value" |> raw
      case I32Add               => "i32.add" |> raw
      case I32Sub               => "i32.sub" |> raw
      case I32Mul               => "i32.mul" |> raw
      case I32Div               => "i32.div_s" |> raw
      case I32Rem               => "i32.rem_s" |> raw
      case I32And               => "i32.and" |> raw
      case I32Or                => "i32.or" |> raw
      case I32Eqz               => "i32.eqz" |> raw
      case I32Lt(signed)        => s"i32.lt_${if signed then "s" else "u"}" |> raw
      case I32Le(signed)        => s"i32.le_${if signed then "s" else "u"}" |> raw
      case I32Gt(signed)        => s"i32.gt_${if signed then "s" else "u"}" |> raw
      case I32Ge(signed)        => s"i32.ge_${if signed then "s" else "u"}" |> raw
      case I32Eq                => "i32.eq" |> raw
      case I32Shl               => "i32.shl" |> raw
      case I32Shr(signed)       => s"i32.shr_${if signed then "s" else "u"}" |> raw
      case I64Add               => "i64.add" |> raw
      case I64Sub               => "i64.sub" |> raw
      case I64Mul               => "i64.mul" |> raw
      case I64Div               => "i64.div_s" |> raw
      case I64Rem               => "i64.rem_s" |> raw
      case I64And               => "i64.and" |> raw
      case I64Or                => "i64.or" |> raw
      case I64Eqz               => "i64.eqz" |> raw
      case I64Lt(signed)        => s"i64.lt_${if signed then "s" else "u"}" |> raw
      case I64Le(signed)        => s"i64.le_${if signed then "s" else "u"}" |> raw
      case I64Gt(signed)        => s"i64.gt_${if signed then "s" else "u"}" |> raw
      case I64Ge(signed)        => s"i64.ge_${if signed then "s" else "u"}" |> raw
      case I64Eq                => "i64.eq" |> raw
      case I64Shl               => "i64.shl" |> raw
      case I64Shr(signed)       => s"i64.shr_${if signed then "s" else "u"}" |> raw
      case F64Add               => "f64.add" |> raw
      case F64Sub               => "f64.sub" |> raw
      case F64Mul               => "f64.mul" |> raw
      case F64Div               => "f64.div" |> raw
      case F64And               => "f64.and" |> raw
      case F64Or                => "f64.or" |> raw
      case F64Eqz               => "f64.eqz" |> raw
      case F64Lt_s              => "f64.lt_s" |> raw
      case F64Le_s              => "f64.le_s" |> raw
      case F64Eq                => "f64.eq" |> raw
      case Drop                 => "drop" |> raw
      // TODO: more structured control flow class
      case If_void          => "if" |> raw
      case If_i32           => "if (result i32)" |> raw
      case Else             => "else" |> raw
      case Block(label, _)  => s"block $$$label" |> raw
      case Loop(label)      => s"loop $$$label" |> raw
      case Br(label)        => s"br $label" |> raw
      case BrIf(label)      => s"br_if $label" |> raw
      case BrTable(labels)   => s"br_table ${labels.mkString(" ")}" |> raw
      case Return           => "return" |> raw
      case End              => "end" |> raw
      case Call(name)       => s"call $$$name" |> raw
      case CallIndrect(typeName) => s"(call_indirect (type $$$typeName))" |> raw
      case Unreachable      => "unreachable" |> raw
      case GetLocal(name)   => s"local.get $$$name" |> raw
      case SetLocal(name)   => s"local.set $$$name" |> raw
      case TeeLocal(name)   => s"local.tee $$$name" |> raw
      case GetGlobal(index) => s"global.get $index" |> raw
      case SetGlobal(index) => s"global.set $index" |> raw
      case I32Store            => "i32.store" |> raw
      case I32Load             => "i32.load" |> raw
      case I64Store            => "i64.store" |> raw
      case I64Load             => "i64.load" |> raw
      case I32Store8           => "i32.store8" |> raw
      case I32Load8_u          => "i32.load8_u" |> raw
      case Comment(s) =>
        stack(s.split('\n').toList.map(s => raw(s";; $s")))
      case LdSym(sym) => s"i32.const ${sym}" |> raw
      case I32LdOffset(offset) => s"i32.load offset=$offset" |> raw
      case I32StOffset(offset) => s"i32.store offset=$offset" |> raw
      case I64LdOffset(offset) => s"i64.load offset=$offset" |> raw
      case I64StOffset(offset) => s"i64.store offset=$offset" |> raw

end MachineInstr
