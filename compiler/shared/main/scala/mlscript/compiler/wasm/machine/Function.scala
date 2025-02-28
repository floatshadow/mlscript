package mlscript.compiler.wasm

import mlscript.compiler.wasm.MachineInstr
import mlscript.compiler.wasm.Type
import mlscript.compiler.wasm.WasmPrinter
import mlscript.utils.shorthands.*

import scala.collection.immutable.ListMap
import scala.collection.mutable.{Map => MutMap}

case class MachineFunction (
    val name: String,
    val args: Ls[(String, Type)],
    val locals: Ls[(String, Type)],
    val retType: Ls[Type],
    val instrs: Ls[MachineInstr],
    val visibility: Bool = true
):
  override def toString: String = show
  
  def updated(newbody: Ls[MachineInstr]) =
    this.copy(instrs = newbody)

  def show: String =
    toDocument.print

  def toDocument: Document
    = WasmPrinter.mkFunction(this)

end MachineFunction