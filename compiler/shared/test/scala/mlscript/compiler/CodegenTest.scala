package mlscript
package compiler

import mlscript.utils.shorthands.*
import scala.util.control.NonFatal
import scala.collection.mutable.StringBuilder
import mlscript.codegen.Helpers.inspect as showStructure
import compiler.backend.Mls2ir
import mlscript.compiler.backend.Ir2wasm
import mlscript.compiler.backend.wasm.CodePrinter
import mlscript.compiler.backend.wasm.ModulePrinter

class CodegenTestCompiler extends DiffTests {
  import CodegenTestCompiler.*
  override def postType(
      output: String => Unit,
      mode: ModeType,
      basePath: List[Str],
      testName: Str,
      unit: TypingUnit,
      typer: Typer
  )(tpd: typer.TypedTypingUnit): List[Str] =
    val outputBuilder = StringBuilder()
    val (blocks, imports, classDefMap, recordDefMap) = new Mls2ir().apply(unit)
    blocks.foreach((bb, _) => output(s"${bb.name} IR:\n${bb.printIR}"))
    val wasmModule = new Ir2wasm().translate(
      blocks,
      testName,
      imports,
      classDefMap,
      recordDefMap
    )
    output(s"\nWASM:\n${ModulePrinter(wasmModule)}\n")
    CodePrinter(wasmModule)
    outputBuilder.toString().linesIterator.toList

  override protected lazy val files = allFiles.filter { file =>
    val fileName = file.baseName
    validExt(file.ext) && filter(file.relativeTo(pwd))
  }
}

object CodegenTestCompiler {

  private val pwd = os.pwd
  private val dir = pwd / "compiler" / "shared" / "test" / "diff" / "codegen"

  private val allFiles = os.walk(dir).filter(_.toIO.isFile)

  private val validExt = Set("fun", "mls")

  private def filter(file: os.RelPath) = DiffTests.filter(file)

}
