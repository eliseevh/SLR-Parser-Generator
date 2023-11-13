package eliseevh.parsergen.parser

import eliseevh.parsergen.grammar._
import eliseevh.parsergen.parser.state.MultiState

import scala.annotation.tailrec

case class Mapping(
    terminals: Map[Terminal, Int],
    nonTerminals: Map[NonTerminal, Int],
    rules: Map[Rule, Int],
    states: Map[MultiState, Int]
)

object Mapping {
  def fromGrammarAndTable(grammar: Grammar, table: Table): Mapping = {
    val terminals = mappingFromList(grammar.terminals.toList)
    val nonTerminals =
      mappingFromList(grammar.nonTerminals.toList, terminals.size)
    val rules = mappingFromList(grammar.rules.toList)
    val allStates = table.terminalPart.keySet.map(
      _._1
    ) ++ table.nonTerminalPart.keySet.map(_._1)
    val states = mappingFromList(allStates.toList)
    Mapping(terminals, nonTerminals, rules, states)
  }

  private def mappingFromList[A](
      list: List[A],
      startValue: Int = 0
  ): Map[A, Int] = {
    @tailrec
    def loop(list: List[(A, Int)], acc: Map[A, Int]): Map[A, Int] = list match {
      case Nil              => acc
      case (v, idx) :: rest => loop(rest, acc + (v -> (idx + startValue)))
    }
    loop(list.zipWithIndex, Map())
  }
}
