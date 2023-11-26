package eliseevh.parsergen.parser

import eliseevh.parsergen.generic.{NonTerminalDescriptor, TerminalDescriptor}
import eliseevh.parsergen.grammar._
import eliseevh.parsergen.parser.state.MultiState

case class Table(
                  terminalPart: Map[(MultiState, TerminalDescriptor[?]), Action],
                  nonTerminalPart: Map[(MultiState, NonTerminalDescriptor[?]), MultiState]
)
