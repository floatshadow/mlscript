package mlscript
package compiler

import mlscript.utils.shorthands.*
import scala.util.control.NonFatal
import scala.collection.mutable.StringBuilder
import mlscript.codegen.Helpers.inspect as showStructure
import compiler.backend.Mls2ir
import mlscript.compiler.backend.Ir2wasm

class CodegenTestCompiler extends DiffTests {
  import CodegenTestCompiler.*
  override def postProcess(
      mode: ModeType,
      basePath: List[Str],
      testName: Str,
      unit: TypingUnit
  ): List[Str] =
    val outputBuilder = StringBuilder()
    outputBuilder ++= "IR:\n"
    try
      val ir = new Mls2ir().apply(unit)
      outputBuilder ++= ir.printIR
      outputBuilder ++= "\nWASM:\n"
      try
        val wasm = new Ir2wasm().translate(ir)
        outputBuilder ++= wasm.toString
      catch err => outputBuilder ++= "Ir2wasm failed: " ++ err.toString()
    catch err => outputBuilder ++= "Mls2ir failed: " ++ err.toString()
    outputBuilder.toString().linesIterator.toList

  override protected lazy val files = allFiles.filter { file =>
    val fileName = file.baseName
    validExt(file.ext) && filter(file.relativeTo(pwd))
  }
}

object CodegenTestCompiler {

  private val pwd = os.pwd
  private val dir = pwd / "compiler" / "shared" / "test" / "diff"/"codegen"

  private val allFiles = os.walk(dir).filter(_.toIO.isFile)

  private val validExt = Set("fun", "mls")

  private def filter(file: os.RelPath) = DiffTests.filter(file)

}
