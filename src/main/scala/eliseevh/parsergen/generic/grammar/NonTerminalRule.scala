package eliseevh.parsergen.generic.grammar

import eliseevh.parsergen.generic.{ElementDescriptor, NonTerminalDescriptor}

case class NonTerminalRule[R](
    descriptor: NonTerminalDescriptor[R],
    rightElements: List[ElementDescriptor[?]],
    conversion: List[Any] => R
)
