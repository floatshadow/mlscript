package mlscript.compiler.backend.wasm

import mlscript.compiler.backend.wasm.WasmInstructions._
import mlscript.compiler.backend.wasm.WasmInstructions
import mlscript.compiler.backend.wasm.Document._
import mlscript.compiler.backend.Type
import scala.language.implicitConversions

// Printer for Wasm modules
object ModulePrinter {
  private implicit def s2d(s: String): Raw = Raw(s)

  private def mkMod(mod: Module): Document = stack(
    "(module ",
    Indented(Raw(s"(import \"system\" \"mem\" (memory 100))")),
    Indented(Stacked(mod.imports map mkImport)),
    Indented("(global (mut i32) i32.const 0) "),
    Indented(Stacked(mod.functions map mkFun)),
    ")"
  )

  private def mkImport(s: String): Document = s match
    case "log" =>
      Stacked(
        List(
          Raw("(func $logI32 (import \"system\" \"logI32\") (param i32 i32))"),
          Raw("(func $logI64 (import \"system\" \"logI64\") (param i64))"),
          Raw("(func $logF64 (import \"system\" \"logF64\") (param f64))")
        )
      )
    case _ => line(List("(import ", s, ")"))

  private def mkFun(fh: Function): Document = {
    val name = fh.name
    val isMain = fh.isMain
    val exportDoc: Document =
      if (isMain) s"""(export "$name" (func $$$name))""" else ""
    val paramsDoc: Document = line(fh.args.map(arg =>
      val tpe = fh.locals(arg.name)._1 match
        case Type.Float64 => "f64"
        case Type.Int64   => "i64"
        case _            => "i32"
      Raw(s"(param $$${arg.name} $tpe) ")
    ))
    val resultDoc: Document =
      if (isMain) ""
      else
        fh.retType match
          case Type.Float64 => "(result f64) "
          case Type.Int64   => "(result i64) "
          case _            => "(result i32) "
    val localsDoc: Document = Indented(
      line(
        fh.locals
          .filter((k, v) => !v._2)
          .map((k, v) =>
            val tpe = v._1 match
              case Type.Float64 => "f64"
              case Type.Int64 => "i64"
              case _            => "i32"
            Raw(s"(local $$$k $tpe) ")
          )
          .toList
      )
    )

    stack(
      exportDoc,
      line(List(s"(func $$${fh.name} ", paramsDoc, resultDoc, localsDoc)),
      Indented(Stacked(mkCode(fh.code))),
      ")"
    )
  }

  private def mkCode(code: Code): List[Document] = code.instructions match {
    case Nil => Nil
    case h :: t =>
      h match {
        case Else =>
          Unindented(mkInstr(h)) ::
            mkCode(t)
        case End =>
          Unindented(mkInstr(h)) ::
            (mkCode(t) map Unindented.apply)
        case If_void | If_i32 | Block(_, _) | Loop(_) =>
          mkInstr(h) ::
            (mkCode(t) map Indented.apply)
        case _ =>
          mkInstr(h) ::
            mkCode(t)
      }
  }

  private def mkInstr(instr: WasmInstruction): Document = {
    instr match {
      case I32Const(value)  => s"i32.const $value"
      case I64Const(value)  => s"i64.const $value"
      case F64Const(value)  => s"f64.const $value"
      case I32Add              => "i32.add"
      case I32Sub              => "i32.sub"
      case I32Mul              => "i32.mul"
      case I32Div              => "i32.div_s"
      case I32Rem              => "i32.rem_s"
      case I32And              => "i32.and"
      case I32Or               => "i32.or"
      case I32Eqz              => "i32.eqz"
      case I32Lt_s             => "i32.lt_s"
      case I32Le_s             => "i32.le_s"
      case I32Eq               => "i32.eq"
      case I64Add              => "i64.add"
      case I64Sub              => "i64.sub"
      case I64Mul              => "i64.mul"
      case I64Div              => "i64.div_s"
      case I64Rem              => "i64.rem_s"
      case I64And              => "i64.and"
      case I64Or               => "i64.or"
      case I64Eqz              => "i64.eqz"
      case I64Lt_s             => "i64.lt_s"
      case I64Le_s             => "i64.le_s"
      case I64Eq               => "i64.eq"
      case F64Add           => "f64.add"
      case F64Sub           => "f64.sub"
      case F64Mul           => "f64.mul"
      case F64Div           => "f64.div"
      case F64And           => "f64.and"
      case F64Or            => "f64.or"
      case F64Eqz           => "f64.eqz"
      case F64Lt_s          => "f64.lt_s"
      case F64Le_s          => "f64.le_s"
      case F64Eq            => "f64.eq"
      case Drop             => "drop"
      case If_void          => "if"
      case If_i32           => "if (result i32)"
      case Else             => "else"
      case Block(label, _)  => s"block $$$label"
      case Loop(label)      => s"loop $$$label"
      case Br(label)        => s"br $$$label"
      case BrTable(layer)   => s"br_table ${(layer to 0 by -1).mkString(" ")}"
      case Return           => "ret"
      case End              => "end"
      case Call(name)       => s"call $$$name"
      case Unreachable      => "unreachable"
      case GetLocal(name)   => s"local.get $$$name"
      case SetLocal(name)   => s"local.set $$$name"
      case GetGlobal(index) => s"global.get $index"
      case SetGlobal(index) => s"global.set $index"
      case I32Store            => "i32.store"
      case I32Load             => "i32.load"
      case I64Store            => "i64.store"
      case I64Load             => "i64.load"
      case Store8           => "i32.store8"
      case Load8_u          => "i32.load8_u"
      case Comment(s) =>
        Stacked(s.split('\n').toList.map(s => Raw(s";; $s")))
    }
  }

  def apply(mod: Module) = mkMod(mod).print
  def apply(fh: Function) = mkFun(fh).print
  def apply(instr: WasmInstruction) = mkInstr(instr).print

}
