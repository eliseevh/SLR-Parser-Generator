package eliseevh.parsergen.grammar.lexer

case class Token[+R](value: R, source: String, descriptor: TerminalDescriptor[R])
