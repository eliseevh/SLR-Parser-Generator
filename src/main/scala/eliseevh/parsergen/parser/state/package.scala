package eliseevh.parsergen.parser

import eliseevh.parsergen.generic.{ElementDescriptor, NonTerminalDescriptor}
import eliseevh.parsergen.generic.grammar.NonTerminalRule
import eliseevh.parsergen.grammar._

import scala.annotation.tailrec

package object state {
  case class SingleState(rule: NonTerminalRule[?], position: Int) {
    val next: Option[ElementDescriptor[?]] = rule.rightElements.lift(position)

    def grow(rules: Set[NonTerminalRule[?]]): Set[SingleState] = next match {
      case Some(v: NonTerminalDescriptor[?]) =>
        rules.collect {
          case rule @ NonTerminalRule(l, _, _) if l == v => SingleState(rule, 0)
        } + this
      case _ => Set(this)
    }

    def nextBy(symbol: ElementDescriptor[?]): Option[SingleState] = next match {
      case Some(s) if s == symbol => Some(this.copy(position = position + 1))
      case _                      => None
    }
  }

  private object SingleState {
    def fromGrammar(grammar: Grammar): SingleState =
      SingleState(Worker.startRule(grammar), 0)
  }

  case class MultiState(states: Set[SingleState]) {
    val isEmpty: Boolean = states.isEmpty

    def shiftVariant(
        rules: Set[NonTerminalRule[?]],
        symbol: ElementDescriptor[?]
    ): MultiState = MultiState
      .grow(rules, states.flatMap(_.nextBy(symbol)))

    def reduceVariants(): Set[NonTerminalRule[?]] = states.collect {
      case state @ SingleState(rule, _) if state.next.isEmpty => rule
    }
  }

  object MultiState {
    @tailrec private def grow(
        rules: Set[NonTerminalRule[?]],
        states: Set[SingleState]
    ): MultiState = {
      val nextStates = states.flatMap(_.grow(rules))
      if (nextStates == states) {
        new MultiState(states)
      } else {
        grow(rules, nextStates)
      }
    }

    def fromGrammar(grammar: Grammar): MultiState = {
      grow(grammar.nonTerminalRules, Set(SingleState.fromGrammar(grammar)))
    }
  }
}
