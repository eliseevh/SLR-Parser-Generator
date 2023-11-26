package eliseevh.parsergen.result

import eliseevh.parsergen.generic.grammar.NonTerminalRule
import eliseevh.parsergen.generic.{ElementDescriptor, NonTerminalDescriptor, TerminalDescriptor}
import eliseevh.parsergen.generic.lexer.Token
import eliseevh.parsergen.grammar._

sealed trait Tree[R] {
  def descriptor: ElementDescriptor[R]
  def value: R
}

final case class Node[R](rule: NonTerminalRule[R], children: List[Tree[?]], value: R) extends Tree[R] {
  override val descriptor: NonTerminalDescriptor[R] = rule.descriptor
}

final case class Leaf[R](token: Token[R]) extends Tree[R] {
  override val descriptor: TerminalDescriptor[R] = token.descriptor
  override val value: R = token.value
}
