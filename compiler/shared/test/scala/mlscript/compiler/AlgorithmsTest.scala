import mlscript.compiler.backend.algorithms._

class AlgorithmsTest extends munit.FunSuite {

  import scala.language.implicitConversions
  implicit def implicitSome[A](x: A): Some[A] = Some(x)

  test("Dominance test 1") {
    val edges = List((1, 2), (2, 3), (3, 4), (4, 5))
    val nodes = List(1, 2, 3, 4, 5)
    val obtained = getImmediateDominator(edges, nodes)
    val expected: Map[Int, Option[Int]] =
      Map(1 -> None, 2 -> 1, 3 -> 2, 4 -> 3, 5 -> 4)
    assertEquals(obtained, expected)
  }

  test("Dominance test 2") {
    val edges = List((1, 2), (1, 3), (2, 3), (3, 4), (3, 5), (4, 5))
    val nodes = List(1, 2, 3, 4, 5)
    val obtained = getImmediateDominator(edges, nodes)
    val expected: Map[Int, Option[Int]] =
      Map(1 -> None, 2 -> 1, 3 -> 1, 4 -> 3, 5 -> 3)
    assertEquals(obtained, expected)
  }

  test("Dominance test 3") {
    val edges =
      List((1, 2), (1, 3), (2, 4), (3, 5), (4, 6), (5, 6), (5, 7), (7, 5))
    val nodes = List(1, 2, 3, 4, 5, 6, 7)
    val obtained = getImmediateDominator(edges, nodes)
    val expected: Map[Int, Option[Int]] =
      Map(1 -> None, 2 -> 1, 3 -> 1, 4 -> 2, 5 -> 3, 6 -> 1, 7 -> 5)
    assertEquals(obtained, expected)
  }

  test("Dominance test 4") {
    val edges = List((1, 2), (1, 3), (2, 4), (3, 4), (4, 5), (5, 2), (5, 3))
    val nodes = List(1, 2, 3, 4, 5)
    val obtained = getImmediateDominator(edges, nodes)
    val expected: Map[Int, Option[Int]] =
      Map(1 -> None, 2 -> 1, 3 -> 1, 4 -> 1, 5 -> 4)
    assertEquals(obtained, expected)
  }

  // Example from "Algorithms for Finding Dominators in Directed Graphs" P.4
  test("Dominance test 5") {
    val edges = List(
      ("C", "D"),
      ("C", "G"),
      ("D", "H"),
      ("D", "A"),
      ("G", "E"),
      ("G", "I"),
      ("H", "J"),
      ("A", "B"),
      ("A", "F"),
      ("I", "E"),
      ("I", "J"),
      ("J", "K"),
      ("K", "C")
    )
    val nodes = List("C", "D", "G", "H", "A", "E", "I", "J", "B", "F", "K")
    val obtained = getImmediateDominator(edges, nodes)
    val expected: Map[String, Option[String]] = Map(
      "C" -> None,
      "D" -> "C",
      "H" -> "D",
      "A" -> "D",
      "B" -> "A",
      "F" -> "A",
      "G" -> "C",
      "E" -> "G",
      "I" -> "G",
      "J" -> "C",
      "K" -> "J"
    )
    assertEquals(obtained, expected)
  }

  test("Reverse Post-order test 1") {
    val edges = List((0, 1), (1, 2), (2, 3), (3, 4))
    val nodes = List(0, 1, 2, 3, 4)
    val obtained = reversePostOrder(edges, nodes)
    val expected = Map(0 -> 0, 1 -> 1, 2 -> 2, 3 -> 3, 4 -> 4)
    assertEquals(obtained, expected)
  }

  test("Reverse Post-order test 2") {
    val edges = List(
      ("C", "D"),
      ("C", "G"),
      ("D", "H"),
      ("D", "A"),
      ("G", "E"),
      ("G", "I"),
      ("H", "J"),
      ("A", "B"),
      ("A", "F"),
      ("I", "E"),
      ("I", "J"),
      ("J", "K"),
      ("K", "C")
    )
    val nodes = List("C", "D", "G", "H", "A", "E", "I", "J", "B", "F", "K")
    val obtained = reversePostOrder(edges, nodes)
    val expected = Map(
      "C" -> 0,
      "G" -> 1,
      "I" -> 2,
      "E" -> 3,
      "D" -> 4,
      "A" -> 5,
      "F" -> 6,
      "B" -> 7,
      "H" -> 8,
      "J" -> 9,
      "K" -> 10
    )
    assertEquals(obtained, expected)
  }

  test("Loop Header test 1") {
    val edges = List(
      ("A", "B"),
      ("B", "C"),
      ("C", "A"),
      ("C", "D"),
      ("D", "E"),
      ("E", "F"),
      ("F", "D")
    )
    val nodes = List("A", "B", "C", "D", "E", "F")
    val obtained = getLoopHeader(edges, nodes)
    val expected = Map(
      "A" -> true,
      "B" -> false,
      "C" -> false,
      "D" -> true,
      "E" -> false,
      "F" -> false
    )
    assertEquals(obtained, expected)
  }

  test("Merged Node test 1") {
    val edges = List(
      ("A", "B"),
      ("A", "D"),
      ("B", "C"),
      ("B", "E"),
      ("C", "F"),
      ("D", "E"),
      ("D", "F"),
      ("E", "F")
    )
    val nodes = List("A", "B", "C", "D", "E", "F")
    val obtained = getMergedNode(edges, nodes)
    val expected = Map(
      "A" -> false,
      "B" -> false,
      "C" -> false,
      "D" -> false,
      "E" -> true,
      "F" -> true
    )
    assertEquals(obtained, expected)
  }
}
