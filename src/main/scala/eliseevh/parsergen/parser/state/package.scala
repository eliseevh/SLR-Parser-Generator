package eliseevh.parsergen.parser

import eliseevh.parsergen.grammar._

import scala.annotation.tailrec

package object state {
  case class SingleState(rule: Rule, position: Int) {
    val next: Option[GrammarSymbol] = rule.rightSide.lift(position)

    def grow(rules: Set[Rule]): Set[SingleState] = next match {
      case Some(v: NonTerminal) => rules.collect { case rule@Rule(l, _) if l == v => SingleState(rule, 0)
      } + this
      case _ => Set(this)
    }

    def nextBy(symbol: GrammarSymbol): Option[SingleState] = next match {
      case Some(s) if s == symbol => Some(this.copy(position = position + 1))
      case _ => None
    }
  }

  private object SingleState {
    def fromGrammar(grammar: Grammar): SingleState = SingleState(Rule(ExtraStarting -> List(grammar.start)), 0)
  }

  case class MultiState(states: Set[SingleState]) {
    val isEmpty: Boolean = states.isEmpty

    def shiftVariant(rules: Set[Rule], symbol: GrammarSymbol): MultiState = MultiState
      .grow(rules, states.flatMap(_.nextBy(symbol)))

    def reduceVariants(): Set[Rule] = states.collect { case state@SingleState(rule, _) if state.next.isEmpty => rule
    }
  }

  object MultiState {
    @tailrec private def grow(rules: Set[Rule], states: Set[SingleState]): MultiState = {
      val nextStates = states.flatMap(_.grow(rules))
      if (nextStates == states) {
        new MultiState(states)
      } else {
        grow(rules, nextStates)
      }
    }

    def fromGrammar(grammar: Grammar): MultiState = {
      grow(grammar.rules, Set(SingleState.fromGrammar(grammar)))
    }
  }}
