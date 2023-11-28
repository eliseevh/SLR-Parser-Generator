package eliseevh.parsergen

import eliseevh.parsergen.grammar.parser.NonTerminalRule
import eliseevh.parsergen.runtime.state.MultiState

package object runtime {
  sealed trait Action extends Serializable

  object Accept extends Action

  final case class Reduce(rule: NonTerminalRule[?]) extends Action

  final case class Shift(nextState: MultiState) extends Action
}
