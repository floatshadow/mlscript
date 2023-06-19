package mlscript.compiler.backend
import scala.annotation.tailrec

object algorithms {

  def getDominator[A](edges: List[(A, A)], nodes: List[A]): Map[A, Set[A]] = {
    val predecessors = edges.groupMap(_._2)(_._1)
    val start = nodes.head

    @tailrec
    def initDom(list: List[A], acc: Map[A, Set[A]]): Map[A, Set[A]] = {
      list match
        case Nil            => acc
        case (node :: rest) => initDom(rest, acc ++ Map(node -> list.toSet))
    }

    var dom: Map[A, Set[A]] = initDom(nodes.reverse, Map.empty[A, Set[A]])
    var changed = true
    val remainNodes = nodes diff List(start)
    while (changed) {
      changed = false
      remainNodes.foreach(node =>
        val predDom: Set[A] = predecessors(node).toList
          .map(pred => dom(pred))
          .reduce((set1, set2) => set1 & set2)
        val newSet = predDom | Set(node)
        if (!newSet.equals(dom(node))) {
          dom += node -> newSet
          changed = true
        }
      )
    }
    dom
  }

  def getImmediateDominator[A](
      edges: List[(A, A)],
      nodes: List[A]
  ): Map[A, Option[A]] = {
    val dom = getDominator(edges, nodes)
    nodes.foldLeft(Map[A, Option[A]]()) { (idom, node) =>
      val nodeSdom = dom(node) - node
      val nodeIdom =
        nodeSdom.filter(M => nodeSdom.forall(P => dom(M).contains(P)))
      idom + (node -> nodeIdom.headOption)
    }
  }

  def reversePostOrder[A](edges: List[(A, A)], nodes: List[A]): Map[A, Int] = {
    var visited = Set.empty[A]

    def dfs(node: A, stack: List[A]): List[A] = {
      visited += node
      val newStack = edges
        .collect {
          case (from, to) if from == node && !visited.contains(to) =>
            dfs(to, stack)
        }
        .foldLeft(stack)(_ ++ _)
      newStack :+ node
    }

    val finalStack = nodes.foldLeft(List.empty[A]) { (stack, node) =>
      if (!visited.contains(node))
        dfs(node, stack)
      else
        stack
    }

    val indexMap = finalStack.reverse.zipWithIndex.toMap
    nodes.map(x => x -> indexMap(x)).toMap
  }

  def getLoopHeader[A](edges: List[(A, A)], nodes: List[A]): Map[A, Boolean] = {
    val dom = getDominator(edges, nodes)
    val headers = edges
      .filter { (from, to) => dom(from).contains(to) }
      .map((from, to) => to)
    nodes.map(node => node -> headers.contains(node)).toMap
  }

  def getMergedNode[A](edges: List[(A, A)], nodes: List[A]): Map[A, Boolean] = {
    val mergedNodes = nodes.filter(node => edges.count(_._2 == node) > 1)
    nodes.map(node => node -> mergedNodes.contains(node)).toMap
  }
}
