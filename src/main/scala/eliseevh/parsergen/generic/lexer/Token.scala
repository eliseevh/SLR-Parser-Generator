package eliseevh.parsergen.generic.lexer

import eliseevh.parsergen.generic.TerminalDescriptor

case class Token[+R](value: R, source: String, descriptor: TerminalDescriptor[R])
