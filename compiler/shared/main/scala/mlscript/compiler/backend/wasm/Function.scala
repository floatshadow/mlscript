package mlscript.compiler.backend.wasm

import mlscript.compiler.backend.wasm.WasmInstructions.Code
import mlscript.compiler.backend.Type
import scala.collection.mutable.Map as MutMap
import mlscript.compiler.backend.Operand

// If isMain = false, represents a function which returns an i32 and will not be exported to js
// If isMain = true , represents a function which does not return a value, and will be exported to js
class Function private (val name: String, val args:  List[Operand.Var], val isMain: Boolean, val locals: Map[String,(Type,Boolean)], val code: Code, val codeGen: LocalsHandler => Code) {
  override def toString: String = ModulePrinter(this)
}

class LocalsHandler(locals:MutMap[String,(Type,Boolean)]) { //TODO change to Operand key
  private var locals_ = locals

  def this() = {
    this(MutMap.empty[String,(Type,Boolean)])
  }

  def this(that:LocalsHandler) = {
    this(that.getLocals)
  }

  def getType(name:String) = locals_(name)._1

  def getIsParam(name:String) = locals_(name)._2

  def register(name:String,tpe:Type, isParam:Boolean = false) = 
    locals_ += (name->(tpe,isParam))
  private[wasm] def getLocals = locals_
}

object Function {
  def apply(name: String, args: List[Operand.Var], isMain: Boolean)(codeGen: LocalsHandler => Code) = {
    val lh = new LocalsHandler()
    // Make code first, as it may increment the locals in lh
    val code = codeGen(lh)
    new Function(name, args, isMain, lh.getLocals.toMap, code, codeGen)
  }
}
