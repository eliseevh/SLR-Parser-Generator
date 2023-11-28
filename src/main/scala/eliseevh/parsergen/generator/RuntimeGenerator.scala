package eliseevh.parsergen.generator

import eliseevh.parsergen.grammar.lexer.TerminalDescriptor
import eliseevh.parsergen.grammar.lexer.TerminalDescriptor.Eof
import eliseevh.parsergen.grammar.parser.NonTerminalDescriptor.ExtraStarting
import eliseevh.parsergen.grammar.parser.{NonTerminalDescriptor, NonTerminalRule}
import eliseevh.parsergen.grammar.{ElementDescriptor, Grammar}
import eliseevh.parsergen.runtime._
import eliseevh.parsergen.runtime.state.MultiState

import scala.annotation.tailrec


case class RuntimeGenerator private(
    parser: Parser,
    lexer: Lexer,
)
/*
   grammar -> first
   grammar, first -> follow
   grammar, follow -> table
   grammar, table -> parser
   grammar -> lexer
 */
object RuntimeGenerator {
  private type FirstType = Map[NonTerminalDescriptor[?], Set[Option[TerminalDescriptor[?]]]]
  private type FollowType = Map[NonTerminalDescriptor[?], Set[Option[TerminalDescriptor[?]]]]

  def apply(
      grammar: Grammar
  ): RuntimeGenerator = {
    val first = getFirst(grammar)
    val follow = getFollow(grammar, first)
    val unitedTable = getUnitedTable(grammar, follow)
    val terminalPart = unitedTable.collect {
      case ((state, terminal: TerminalDescriptor[?]), action) => (state, terminal) -> action
    }
    val nonTerminalPart = unitedTable.collect {
      case ((state, nonTerminal: NonTerminalDescriptor[?]), Shift(nextState)) =>
        (state, nonTerminal) -> nextState
    }
    val table = Table(terminalPart, nonTerminalPart)
    val parser = Parser(table, MultiState.fromGrammar(grammar))
    RuntimeGenerator(
      parser,
      Lexer(grammar.terminalRules),
    )
  }

  private def applyFirst(
      first: FirstType,
      seq: List[ElementDescriptor[?]]
  ): Set[Option[TerminalDescriptor[?]]] = {
    @tailrec def loop(
        symbols: List[ElementDescriptor[?]],
        got: Set[Option[TerminalDescriptor[?]]]
    ): Set[Option[TerminalDescriptor[?]]] = symbols match {
      case Nil                      => got + None
      case Eof :: _                 => got
      case (v: TerminalDescriptor[?]) :: _ => got + Some(v)
      case (v: NonTerminalDescriptor[?]) :: rest =>
        val firstV = first(v)
        if (firstV(None)) {
          loop(rest, got ++ (firstV - None))
        } else {
          got ++ firstV
        }
    }

    loop(seq, Set())
  }

  private def getFirst(grammar: Grammar): FirstType = {
    val rules = grammar.nonTerminalRules + startRule(grammar)
    @tailrec
    def loop(first: FirstType): FirstType = {
      val nextFirst = seqToMap(
        first.toSeq ++ rules.toSeq
          .map { case NonTerminalRule(leftSide, rightSide, _) =>
            leftSide -> applyFirst(first, rightSide)
          }
      )
      if (nextFirst == first) {
        first
      } else {
        loop(nextFirst)
      }
    }
    val initial: FirstType = (Map.from(
      grammar.nonTerminals.map(_ -> Set())
    ): FirstType) + (ExtraStarting -> Set())
    loop(initial)
  }

  private def getFollow(grammar: Grammar, first: FirstType): FollowType = {
    val rules = grammar.nonTerminalRules + startRule(grammar)
    val initial: FollowType =
      (Map.from(
        grammar.nonTerminals.map(_ -> Set())
      ): FollowType) + (ExtraStarting -> Set(Some(Eof)))
    @tailrec
    def loop(follow: FollowType): FollowType = {
      val nextFollow =
        seqToMap(follow.toSeq ++ rules.toSeq.flatMap {
          case NonTerminalRule(leftSide, rightSide, _) =>
            rightSide.scanRight(Nil: List[ElementDescriptor[?]])(_ :: _).collect {
              case (nonTerminal: NonTerminalDescriptor[?]) :: rest =>
                val firstOfRest = applyFirst(first, rest)
                val additional = if (firstOfRest.contains(None)) {
                  follow(leftSide)
                } else {
                  Set()
                }
                nonTerminal -> ((firstOfRest - None) ++ additional)
            }
        })
      if (nextFollow == follow) {
        follow
      } else {
        loop(nextFollow)
      }
    }
    loop(initial)
  }

  private def getUnitedTable(
      grammar: Grammar,
      follow: FollowType
  ): Map[(MultiState, ElementDescriptor[?]), Action] = {
    val rules = grammar.nonTerminalRules + startRule(grammar)
    // Return used states and row with that state
    def fillRow(
        state: MultiState
    ): (Set[MultiState], Map[ElementDescriptor[?], Action]) = {
      val row: Map[ElementDescriptor[?], Action] = Map
        .from(for {
          symbol <- grammar.nonTerminals ++ grammar.terminals + Eof
        } yield symbol -> {
          val shiftVariant = state.shiftVariant(rules, symbol)
          val default =
            if (shiftVariant.isEmpty) None else Some(Shift(shiftVariant))
          symbol match {
            case terminal: TerminalDescriptor[?] =>
              val reduceVariants = state
                .reduceVariants()
                .filter { case NonTerminalRule(leftSide, _, _) =>
                  follow(leftSide)(Some(terminal))
                }
                .toList
              reduceVariants match {
                case Nil                                      => default
                case List(rule) if rule == startRule(grammar) => Some(Accept)
                case List(rule)                               => Some(Reduce(rule))
                case _ =>
                  throw new RuntimeException(
                    "Reduce/reduce conflict! Not an SLR grammar"
                  )
              }
            case _ =>
              default
          }
        })
        .collect { case (key, Some(value)) =>
          key -> value
        }
      (
        row.values.collect { case Shift(state) =>
          state
        }.toSet,
        row
      )
    }
    @tailrec
    def loop(
        table: Map[(MultiState, ElementDescriptor[?]), Action],
        left: List[MultiState]
    ): Map[(MultiState, ElementDescriptor[?]), Action] = {
      left match {
        case Nil => table
        case state :: rest =>
          val (newStates, newRow) = fillRow(state)
          val newLeft =
            (newStates -- left -- table.keys.map(_._1)).toList ++ rest
          loop(
            table ++ newRow.map { case (col, value) =>
              (state, col) -> value
            },
            newLeft
          )
      }
    }
    loop(Map(), List(MultiState.fromGrammar(grammar)))
  }

  def startRule(grammar: Grammar): NonTerminalRule[?] = NonTerminalRule(
    ExtraStarting,
    List(grammar.start),
    {case List(v) => v}
  )
  private def seqToMap[A, B](seq: Seq[(A, Set[B])]): Map[A, Set[B]] =
    seq.groupMapReduce(_._1)(_._2)(_ ++ _)
}
