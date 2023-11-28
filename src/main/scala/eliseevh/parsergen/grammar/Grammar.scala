package eliseevh.parsergen.grammar

import eliseevh.parsergen.grammar.lexer.{TerminalDescriptor, TerminalRule}
import eliseevh.parsergen.grammar.parser.{NonTerminalDescriptor, NonTerminalRule}

case class Grammar(
    start: NonTerminalDescriptor[?],
    nonTerminalRules: Set[NonTerminalRule[?]],
    terminalRules: List[TerminalRule[?]],
    nonTerminals: Set[NonTerminalDescriptor[?]],
    terminals: Set[TerminalDescriptor[?]]
)
