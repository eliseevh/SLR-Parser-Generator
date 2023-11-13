package eliseevh.parsergen.parser

import eliseevh.parsergen.grammar._
import eliseevh.parsergen.parser.state.MultiState

case class Table(
    terminalPart: Map[(MultiState, Terminal), Action],
    nonTerminalPart: Map[(MultiState, NonTerminal), MultiState]
)
