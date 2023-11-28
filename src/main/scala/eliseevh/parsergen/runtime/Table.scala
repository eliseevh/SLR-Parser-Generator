package eliseevh.parsergen.runtime

import eliseevh.parsergen.grammar.lexer.TerminalDescriptor
import eliseevh.parsergen.grammar.parser.NonTerminalDescriptor
import eliseevh.parsergen.runtime.Action
import eliseevh.parsergen.runtime.state.MultiState

case class Table(
    terminalPart: Map[(MultiState, TerminalDescriptor[?]), Action],
    nonTerminalPart: Map[(MultiState, NonTerminalDescriptor[?]), MultiState]
)
