package eliseevh.parsergen

import eliseevh.parsergen.generic.grammar.NonTerminalRule
import eliseevh.parsergen.parser.state.MultiState

package object parser {
  sealed trait Action extends Serializable

  object Accept extends Action

  final case class Reduce(rule: NonTerminalRule[?]) extends Action

  final case class Shift(nextState: MultiState) extends Action
}
