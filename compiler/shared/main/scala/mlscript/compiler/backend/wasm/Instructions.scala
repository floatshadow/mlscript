package mlscript.compiler.backend.wasm

import scala.language.implicitConversions

// A subset of instructions defined by the WASM standard
object WasmInstructions {
  abstract class WasmInstruction

  // Load an int32 constant to the stack
  case class I32Const(value: Int) extends WasmInstruction
  case class I64Const(value: Int) extends WasmInstruction
  case class F64Const(value: Float) extends WasmInstruction

  // Numeric/logical instructions
  case object I32Add extends WasmInstruction
  case object I32Sub extends WasmInstruction
  case object I32Mul extends WasmInstruction
  case object I32Div extends WasmInstruction
  case object I32Rem extends WasmInstruction
  case object I32And extends WasmInstruction
  case object I32Or extends WasmInstruction
  case object I32Eqz extends WasmInstruction
  case object I32Lt_s extends WasmInstruction
  case object I32Le_s extends WasmInstruction
  case object I32Eq extends WasmInstruction
  case object I64Add extends WasmInstruction
  case object I64Sub extends WasmInstruction
  case object I64Mul extends WasmInstruction
  case object I64Div extends WasmInstruction
  case object I64Rem extends WasmInstruction
  case object I64And extends WasmInstruction
  case object I64Or extends WasmInstruction
  case object I64Eqz extends WasmInstruction
  case object I64Lt_s extends WasmInstruction
  case object I64Le_s extends WasmInstruction
  case object I64Eq extends WasmInstruction
  case object Drop extends WasmInstruction // Drops the top value of the stack
  case object F64Add extends WasmInstruction
  case object F64Sub extends WasmInstruction
  case object F64Mul extends WasmInstruction
  case object F64Div extends WasmInstruction
  case object F64Rem extends WasmInstruction
  case object F64And extends WasmInstruction
  case object F64Or extends WasmInstruction
  case object F64Eqz
      extends WasmInstruction // Return 1 if operand is 0, 0 otherwise
  case object F64Lt_s extends WasmInstruction // Signed less-than
  case object F64Le_s extends WasmInstruction // Signed less-equals
  case object F64Eq extends WasmInstruction

  // Control instructions
  case object If_void
      extends WasmInstruction // Marks the beginning of an if-block (with implicit 'then').
  case object If_i32
      extends WasmInstruction // Marks the beginning of an if-block (with implicit 'then'). Must leave an i32 on the stack
  case object Else
      extends WasmInstruction // Marks the end of the implicit 'then' of an if-block
  case object End
      extends WasmInstruction // Marks the end of an if-then-else or block
  case class Loop(label: String)
      extends WasmInstruction // A block of instructions with a label at the beginning
  case class Block(label: String, args: List[String])
      extends WasmInstruction // A block of instructions with a label at the end
  case class Br(label: String)
      extends WasmInstruction // Jump to "label", which MUST be the label of an enclosing structure
  case class BrTable(layer: Int) extends WasmInstruction
  case class Call(name: String) extends WasmInstruction
  case object Return extends WasmInstruction
  case object Unreachable extends WasmInstruction // Always fails the program

  // Locals (parameters, local variables)
  case class GetLocal(index: String) extends WasmInstruction
  case class SetLocal(index: String) extends WasmInstruction

  // Global variables
  case class GetGlobal(index: Int) extends WasmInstruction
  case class SetGlobal(index: Int) extends WasmInstruction

  // Memory
  // Stores an i32/i64 to memory. Expects memory address, then stored value as operands
  case object I32Store extends WasmInstruction
  case object I64Store extends WasmInstruction
  // Loads an i32/i64 to memory. Expects memory address as operand
  case object I32Load extends WasmInstruction
  case object I64Load extends WasmInstruction
  // Stores a single byte to memory (the least significant byte of the operand)
  // Operands expected are like Store
  case object Store8 extends WasmInstruction
  // Load a byte from memory, then zero-extend it to fill an i32
  case object Load8_u extends WasmInstruction

  // Comment
  case class Comment(msg: String) extends WasmInstruction

  // Represents a sequence of instructions
  case class Code(instructions: List[WasmInstruction]) {
    def <:>(i: WasmInstruction) = Code(instructions :+ i)
    def <:>(other: Code) = Code(this.instructions ++ other.instructions)
  }

  // Useful implicit conversions to construct Code objects
  implicit def i2c(i: WasmInstruction): Code = Code(List(i))
  implicit def is2c(is: List[WasmInstruction]): Code = Code(is)
  implicit def cs2c(cs: List[Code]): Code = Code(cs flatMap (_.instructions))
}
