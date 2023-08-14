package mlscript.compiler

import scala.collection.mutable.Map as MutMap
import mlscript.compiler.backend._
import mlscript.compiler.backend.BinOpKind._
import mlscript.compiler.backend.Instruction._
import mlscript.compiler.backend.Operand._
import mlscript.compiler.backend.PureValue._
import mlscript.compiler.backend.Type._
import scala.collection.mutable.ListBuffer
import mlscript.IntLit
import scala.collection.mutable.ListMap

class InterBlockTest extends munit.FunSuite {

  import scala.language.implicitConversions
  implicit def implicitRecord(fields: MutMap[String, Type]): Record =
    Record(RecordObj(fields))

  test("Def-Use 1") {
    val thrown = intercept[Exception] {
      val blk_3: BasicBlock = BasicBlock(
        "blk_3",
        List(),
        ListBuffer(
          Return(Some(Var("x2")))
        )
      )
      val blk_2: BasicBlock = BasicBlock(
        "blk_2",
        List(),
        ListBuffer(
          Branch(blk_3, List(Var("x0"), Var("x1"), Var("x2")))
        )
      )
      val blk_1: BasicBlock = BasicBlock(
        "blk_1",
        List(),
        ListBuffer(
          Assignment(Var("x1"), Alloc(Int64)),
          Branch(blk_2, List(Var("x0"), Var("x1")))
        )
      )
      val blk_0: BasicBlock = BasicBlock(
        "blk_0",
        List(),
        ListBuffer(
          Assignment(Var("x0"), Alloc(Int64)),
          Branch(blk_1, List(Var("x0")))
        )
      )
      Verifier.apply(blk_0)
    }
    val expected = "Variable x2 is undefined"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Def-Use 2") {
    val thrown = intercept[Exception] {
      val blk_2: BasicBlock = BasicBlock(
        "blk_2",
        List(),
        ListBuffer(
          Return(Some(Var("x1")))
        )
      )
      val blk_1: BasicBlock = BasicBlock(
        "blk_1",
        List(),
        ListBuffer(
          Assignment(Var("x1"), Alloc(Boolean)),
          Branch(blk_2, List())
        )
      )
      val blk_0: BasicBlock = BasicBlock(
        "blk_0",
        List(),
        ListBuffer(
          Assignment(Var("x0"), Alloc(Boolean)),
          Match(
            Var("x0"),
            ListMap(IntLit(1) -> (blk_1, Nil)),
            (blk_2, Nil)
          )
        )
      )
      Verifier.apply(blk_0)
    }
    val expected = "Variable x1 is undefined"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Def-Use 3") {
    val thrown = intercept[Exception] {
      val blk_3: BasicBlock = BasicBlock(
        "blk_3",
        List(),
        ListBuffer(
          Return(Some(Var("x1")))
        )
      )
      val blk_2: BasicBlock = BasicBlock(
        "blk_2",
        List(),
        ListBuffer(
          Assignment(Var("x1"), Alloc(Boolean)),
          Branch(blk_3, List())
        )
      )
      val blk_1: BasicBlock = BasicBlock(
        "blk_1",
        List(),
        ListBuffer(
          Assignment(Var("x1"), Alloc(Boolean)),
          Branch(blk_3, List())
        )
      )
      val blk_0: BasicBlock = BasicBlock(
        "blk_0",
        List(),
        ListBuffer(
          Assignment(Var("x0"), Alloc(Boolean)),
          Match(
            Var("x0"),
            ListMap(IntLit(1) -> (blk_1, Nil)),
            (blk_2, Nil)
          )
        )
      )
      Verifier.apply(blk_0)
    }
    val expected = "Variable x1 is undefined"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Def-Use 4") {
    val thrown = intercept[Exception] {
      val blk_1: BasicBlock = BasicBlock(
        "blk_1",
        List(),
        ListBuffer(
          Assignment(Var("x0"), Alloc(Int64)),
          Return(None)
        )
      )
      val blk_0: BasicBlock = BasicBlock(
        "blk_0",
        List(),
        ListBuffer(
          Assignment(Var("x0"), Alloc(Boolean)),
          Branch(blk_1, List())
        )
      )
      Verifier.apply(blk_0)
    }
    val expected = "Variable x0 has been defined"
    assertEquals(thrown.getMessage(), expected)
  }
}
