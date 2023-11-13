package eliseevh.parsergen.parser

import eliseevh.parsergen.grammar._
import eliseevh.parsergen.lexer.Lexer
import eliseevh.parsergen.parser.Worker.{FirstType, FollowType}
import eliseevh.parsergen.parser.state.MultiState

import scala.annotation.tailrec

/*
   grammar -> first
   grammar, first -> follow
   grammar, follow -> table
   grammar, table -> pre-parser
   grammar, table -> mapping
   pre-parser, mapping -> parser
 */
case class Worker private (
                            grammar: Grammar,
                            first: FirstType,
                            follow: FollowType,
                            table: Table,
                            preParser: Parser,
                            lexer: Lexer,
                            mapping: Mapping
)

object Worker {
  type FirstType = Map[NonTerminal, Set[Option[NormalTerminal]]]
  type FollowType = Map[NonTerminal, Set[Option[Terminal]]]
  def apply(
      grammar: Grammar,
      lexerGrammar: eliseevh.parsergen.lexer.Grammar
  ): Worker = {
    val first = getFirst(grammar)
    val follow = getFollow(grammar, first)
    val unitedTable = getUnitedTable(grammar, follow)
    val terminalPart = unitedTable.collect {
      case ((state, terminal: Terminal), action) => (state, terminal) -> action
    }
    val nonTerminalPart = unitedTable.collect {
      case ((state, nonTerminal: NonTerminal), Shift(nextState)) =>
        (state, nonTerminal) -> nextState
    }
    val table = Table(terminalPart, nonTerminalPart)
    val preParser = Parser(table, MultiState.fromGrammar(grammar))
    val mapping = Mapping.fromGrammarAndTable(grammar, table)
    Worker(
      grammar,
      first,
      follow,
      table,
      preParser,
      Lexer(lexerGrammar),
      mapping
    )
  }

  private def applyFirst(
      first: FirstType,
      seq: List[GrammarSymbol]
  ): Set[Option[NormalTerminal]] = {
    @tailrec def loop(
        symbols: List[GrammarSymbol],
        got: Set[Option[NormalTerminal]]
    ): Set[Option[NormalTerminal]] = symbols match {
      case Nil                      => got + None
      case (v: NormalTerminal) :: _ => got + Some(v)
      case Eof :: _                 => got
      case (v: NonTerminal) :: rest =>
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
    val rules = grammar.rules + startRule(grammar)
    @tailrec
    def loop(first: FirstType): FirstType = {
      val nextFirst = seqToMap(
        first.toSeq ++ rules.toSeq
          .map { case Rule(leftSide, rightSide) =>
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
    val rules = grammar.rules + startRule(grammar)
    val initial: FollowType =
      (Map.from(
        grammar.nonTerminals.map(_ -> Set())
      ): FollowType) + (ExtraStarting -> Set(Some(Eof)))
    @tailrec
    def loop(follow: FollowType): FollowType = {
      val nextFollow =
        seqToMap(follow.toSeq ++ rules.toSeq.flatMap {
          case Rule(leftSide, rightSide) =>
            rightSide.scanRight(Nil: List[GrammarSymbol])(_ :: _).collect {
              case (nonTerminal: NonTerminal) :: rest =>
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
  ): Map[(MultiState, GrammarSymbol), Action] = {
    val rules = grammar.rules + startRule(grammar)
    // Return used states and row with that state
    def fillRow(
        state: MultiState
    ): (Set[MultiState], Map[GrammarSymbol, Action]) = {
      val row: Map[GrammarSymbol, Action] = Map
        .from(for {
          symbol <- grammar.nonTerminals ++ grammar.terminals
        } yield symbol -> {
          val shiftVariant = state.shiftVariant(rules, symbol)
          val default =
            if (shiftVariant.isEmpty) None else Some(Shift(shiftVariant))
          symbol match {
            case terminal: Terminal =>
              val reduceVariants = state
                .reduceVariants()
                .filter { case Rule(leftSide, _) =>
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
        table: Map[(MultiState, GrammarSymbol), Action],
        left: List[MultiState]
    ): Map[(MultiState, GrammarSymbol), Action] = {
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

  private def startRule(grammar: Grammar): Rule = Rule(
    ExtraStarting -> List(grammar.start)
  )
  private def seqToMap[A, B](seq: Seq[(A, Set[B])]): Map[A, Set[B]] =
    seq.groupMapReduce(_._1)(_._2)(_ ++ _)
}
