package eliseevh.parsergen.grammar

import eliseevh.parsergen.generic.grammar.NonTerminalRule
import eliseevh.parsergen.generic.lexer.TerminalRule
import eliseevh.parsergen.generic.{NonTerminalDescriptor, TerminalDescriptor}

case class Grammar(
    start: NonTerminalDescriptor[?],
    nonTerminalRules: Set[NonTerminalRule[?]],
    terminalRules: Set[TerminalRule[?]],
    nonTerminals: Set[NonTerminalDescriptor[?]],
    terminals: Set[TerminalDescriptor[?]]
)
