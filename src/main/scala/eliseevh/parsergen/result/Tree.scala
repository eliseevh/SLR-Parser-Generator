package eliseevh.parsergen.result

import eliseevh.parsergen.grammar._
import eliseevh.parsergen.lexer.Token

sealed trait Tree {
  def symbol: GrammarSymbol
}

final case class Node(rule: Rule, children: List[Tree]) extends Tree {
  override val symbol: NonTerminal = rule.leftSide
}

final case class Leaf(token: Token) extends Tree {
  override val symbol: Terminal = token.terminal
}
