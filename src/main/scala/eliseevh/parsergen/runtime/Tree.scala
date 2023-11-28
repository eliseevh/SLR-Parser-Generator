package eliseevh.parsergen.runtime

import eliseevh.parsergen.grammar.ElementDescriptor
import eliseevh.parsergen.grammar.lexer.{TerminalDescriptor, Token}
import eliseevh.parsergen.grammar.parser.{
  NonTerminalDescriptor,
  NonTerminalRule
}

sealed trait Tree[R] {
  def descriptor: ElementDescriptor[R]
  def value: R
}

final case class Leaf[R](token: Token[R]) extends Tree[R] {
  override val descriptor: TerminalDescriptor[R] = token.descriptor
  override val value: R = token.value
}

final case class Node[R](
    rule: NonTerminalRule[R],
    children: List[Tree[?]],
    value: R
) extends Tree[R] {
  override val descriptor: NonTerminalDescriptor[R] = rule.descriptor
}
