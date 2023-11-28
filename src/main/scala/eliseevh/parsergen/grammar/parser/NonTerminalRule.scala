package eliseevh.parsergen.grammar.parser

import eliseevh.parsergen.grammar.ElementDescriptor

case class NonTerminalRule[R](
    descriptor: NonTerminalDescriptor[R],
    rightElements: List[ElementDescriptor[?]],
    conversion: List[Any] => R
)
