package mlscript.compiler

import scala.collection.mutable.Map as MutMap
import mlscript.compiler.backend._
import mlscript.compiler.backend.BinOpKind._
import mlscript.compiler.backend.Instruction._
import mlscript.compiler.backend.Operand._
import mlscript.compiler.backend.PureValue._
import mlscript.compiler.backend.Type._
import scala.collection.mutable.ListBuffer

class IntraBlockTest extends munit.FunSuite {

  import scala.language.implicitConversions
  implicit def implicitRecord(fields: MutMap[String, Type]): Record =
    Record(RecordObj(fields))

  test("Undefined variable 1") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), BinOp(And, Const(false), Var("x1"))),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected =
      "key not found: x1 in instruction Assignment(x,And false, x1)"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Undefined variable 2") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), Neg(Var("x2"))),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "key not found: x2 in instruction Assignment(x,neg x2)"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Undefined variable 3") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), GetField(Var("x3"), "field")),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected =
      "key not found: x3 in instruction Assignment(x,getfield x3, field)"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Undefined variable 4") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), IsType(Var("x4"), Float32)),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected =
      "key not found: x4 in instruction Assignment(x,istype x4, f32)"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Undefined variable 5") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), Cast(Var("x5"), Float32)),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "key not found: x5 in instruction Assignment(x,cast x5, f32)"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Undefined variable 6") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), IsVariant(Var("x6"), "variant1")),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected =
      "key not found: x6 in instruction Assignment(x,isvariant x6, variant1)"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Undefined variable 7") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), AsVariant(Var("x7"), "variant1")),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected =
      "key not found: x7 in instruction Assignment(x,asvariant x7, variant1)"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Undefined variable 8") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), Op(Var("x8"))),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "key not found: x8 in instruction Assignment(x,x8)"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Undefined variable 9") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), BinOp(Add, Var("y"), Var("y"))),
            Assignment(Var("y"), Alloc(Int32)),
            Return(Some(Var("x")))
          )
        )
      )
    }
    val expected = "key not found: y in instruction Assignment(x,Add y, y)"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Redefined variable 1") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("y"), Alloc(Int32)),
            Assignment(Var("x"), BinOp(Add, Var("y"), Var("y"))),
            Assignment(Var("x"), BinOp(Add, Var("y"), Var("y"))),
            Return(Some(Var("x")))
          )
        )
      )
    }
    val expected = "Variable x has been defined"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Self-reference variable 1") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), IsType(Var("x"), Boolean)),
            Return(Some(Var("x")))
          )
        )
      )
    }
    val expected = "Variable x cannot be used in its definition"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Type checker 1") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), BinOp(Add, Const(1), Const(true))),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "Inconsistent type in binary operator found: Add i32 bool"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Type checker 2") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), BinOp(Div, Const(true), Const(false))),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "Binary operator does not accept type found: Div bool bool"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Type checker 3") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("printInt"), Alloc(Type.Unit)),
            Assignment(Var("printBool"), Alloc(Type.Unit)),
            Assignment(
              Var("y"),
              BinOp(And, Var("printInt"), Var("printBool"))
            ),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "Binary operator does not accept type found: And () ()"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Type checker 4") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x1"), Alloc(MutMap("id" -> Int32))),
            Assignment(Var("x2"), Alloc(MutMap("id" -> Int32))),
            Assignment(Var("y"), BinOp(Eq, Var("x1"), Var("x2"))),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected =
      "Inconsistent type in binary operator found: Eq {id: i32} {id: i32}"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Type checker 5") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x1"), Alloc(MutMap("id" -> Int32))),
            Assignment(Var("x2"), Alloc(Int32)),
            Assignment(Var("y"), BinOp(Eq, Var("x1"), Var("x2"))),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected =
      "Inconsistent type in binary operator found: Eq {id: i32} i32"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Type checker 6") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), BinOp(Lt, Const(false), Const(false))),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "Binary operator does not accept type found: Lt bool bool"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Type checker 7") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), Neg(Const(""))),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "Negation does not accept type Any"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Type checker 8") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), Alloc(Int32)),
            Assignment(Var("y"), GetField(Var("x"), "id")),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "GetField is not allowed in type i32"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Type checker 9") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), Alloc(MutMap("id" -> Int32))),
            Assignment(Var("y"), GetField(Var("x"), "name")),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "{id: i32} does not contain field name"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Type checker 10") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), Alloc(Int32)),
            Assignment(Var("y"), IsVariant(Var("x"), "people")),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "IsVariant is not allowed in type i32"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Type checker 11") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), Alloc(Int32)),
            Assignment(Var("y"), AsVariant(Var("x"), "people")),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "AsVariant is not allowed in type i32"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Type checker 12") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(
              Var("x"),
              Alloc(
                Variant(
                  VariantObj(
                    MutMap(
                      "people" -> Some(MutMap("age" -> Int32)),
                      "adult" -> Some(
                        MutMap("age" -> Int32, "marriage" -> Boolean)
                      )
                    )
                  )
                )
              )
            ),
            Assignment(Var("y"), AsVariant(Var("x"), "child")),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected =
      "<adult: {age: i32, marriage: bool} | people: {age: i32}> does not contain variant child"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Type checker 13") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(
              Var("record"),
              Alloc(Record(RecordObj(MutMap("id" -> Int32))))
            ),
            SetField(Var("record"), "id", Const(false)),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "record.id expected type:i32 Found:bool"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Type checker 14") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(
              Var("record"),
              Alloc(Record(RecordObj(MutMap("id" -> Int32))))
            ),
            SetField(Var("record"), "name", Const(false)),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "{id: i32} does not contain field name"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Type checker 15") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(
              Var("record"),
              Alloc(Int32)
            ),
            SetField(Var("record"), "name", Const(false)),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "SetField is not allowed in type i32"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Type checker 16") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(
              Var("fun"),
              Alloc(Function(List(Type.Int32), Type.Unit))
            ),
            Call(Some(Var("x")), Var("fun"), List(Const(1))),
            Assignment(Var("y"), Neg(Var("x"))),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "Negation does not accept type ()"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Fun call 1") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(
              Var("fun"),
              Alloc(Function(List(), Type.Unit))
            ),
            Call(None, Var("fun"), List(Const(true))),
            Return(Some(Const(0)))
          )
        )
      )
    }
    val expected = "Function fun incorrect number of arguments"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Termination 1") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("x"), Alloc(Int32))
          )
        )
      )
    }
    val expected =
      "Unterminated basic block. The block ends with instruction: Assignment(x,alloc i32)"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Termination 2") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(Var("y"), Alloc(Type.Int32)),
            Assignment(
              Var("fn"),
              Alloc(Type.Function(List(Type.Int32, Type.Int32), Type.Boolean))
            ),
            Call(Some(Var("x")), Var("fn"), List(Var("y"), Const(10)))
          )
        )
      )
    }
    val expected =
      "Unterminated basic block. The block ends with instruction: Call(Some(x),fn,List(y, 10))"
    assertEquals(thrown.getMessage(), expected)
  }

  test("Termination 3") {
    val thrown = intercept[Exception] {
      Verifier.apply(
        BasicBlock(
          "blk_0",
          List(),
          ListBuffer(
            Assignment(
              Var("record"),
              Alloc(MutMap("id" -> Int32))
            ),
            SetField(Var("record"), "id", Const(123))
          )
        )
      )
    }
    val expected =
      "Unterminated basic block. The block ends with instruction: SetField(record,id,123)"
    assertEquals(thrown.getMessage(), expected)
  }
}
