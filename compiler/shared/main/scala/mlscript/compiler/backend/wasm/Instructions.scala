package mlscript.compiler.backend.wasm

import scala.language.implicitConversions

// A subset of instructions defined by the WASM standard
object WasmInstructions {
  abstract class WasmInstruction

  // Load an int32 constant to the stack
  case class I32Const(value: Int) extends WasmInstruction
  case class F32Const(value: Float) extends WasmInstruction

  // Numeric/logical instructions (all take i32 operands)
  case object Add  extends WasmInstruction
  case object Sub  extends WasmInstruction
  case object Mul  extends WasmInstruction
  case object Div  extends WasmInstruction
  case object Rem  extends WasmInstruction
  case object And  extends WasmInstruction
  case object Or   extends WasmInstruction
  case object Eqz  extends WasmInstruction // Return 1 if operand is 0, 0 otherwise
  case object Lt_s extends WasmInstruction // Signed less-than
  case object Le_s extends WasmInstruction // Signed less-equals
  case object Eq   extends WasmInstruction
  case object Drop extends WasmInstruction // Drops the top value of the stack

  // Control instructions
  case object If_void extends WasmInstruction // Marks the beginning of an if-block (with implicit 'then').
  case object If_i32  extends WasmInstruction // Marks the beginning of an if-block (with implicit 'then'). Must leave an i32 on the stack
  case object Else    extends WasmInstruction // Marks the end of the implicit 'then' of an if-block
  case object End     extends WasmInstruction // Marks the end of an if-then-else or block
  case class Loop(label: Option[String])  extends WasmInstruction // A block of instructions with a label at the beginning
  case class Block(label: Option[String]) extends WasmInstruction // A block of instructions with a label at the end
  case class Br(label: Int)    extends WasmInstruction // Jump to "label", which MUST be the label of an enclosing structure
  case class Call(name: String)   extends WasmInstruction
  case object Return              extends WasmInstruction
  case object Unreachable         extends WasmInstruction // Always fails the program

  // Locals (parameters, local variables)
  case class GetLocal(index: Int) extends WasmInstruction
  case class SetLocal(index: Int) extends WasmInstruction

  // Global variables
  case class GetGlobal(index: Int) extends WasmInstruction
  case class SetGlobal(index: Int) extends WasmInstruction

  // Memory
  // Stores an i32 to memory. Expects memory address, then stored value as operands
  case object Store extends WasmInstruction
  // Loads an i32 to memory. Expects memory address as operand
  case object Load  extends WasmInstruction
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
