package eliseevh.parsergen.parser

import eliseevh.parsergen.grammar._
import eliseevh.parsergen.lexer.Token
import eliseevh.parsergen.parser.state.MultiState
import eliseevh.parsergen.result._

import scala.annotation.tailrec

case class Parser(table: Table, startState: MultiState) {
  private case class ParserState(
      table: Table,
      state: MultiState,
      stack: List[Either[Tree, MultiState]],
      lookahead: Token,
      input: List[Token]
  ) {
    private def next: Either[ParserState, Tree] =
      table.terminalPart((state, lookahead.terminal)) match {
        case Accept =>
          if (stack.size == 2) {
            Right(stack.head.left.toOption.get)
          } else {
            throw new RuntimeException("Non-empty stack at the end")
          }
        case Shift(nextState) =>
          lookahead.terminal match {
            case Eof => throw new RuntimeException("Unexpected Eof")
            case _: NormalTerminal =>
              Left(
                ParserState(
                  table,
                  nextState,
                  Left(Leaf(lookahead)) :: Right(state) :: stack,
                  input.head,
                  input.tail
                )
                )
          }
        case Reduce(reducingRule @ Rule(lhs, rhs)) =>
          @tailrec def take(
              stack: List[Either[Tree, MultiState]],
              rule: Seq[GrammarSymbol],
              collect: List[Tree]
          ): (List[Either[Tree, MultiState]], Tree) = {
            rule match {
              case symbol :: restRule =>
                stack match {
                  case Right(_) :: Left(stackSymbol) :: restStack =>
                    if (stackSymbol.symbol == symbol) {
                      take(restStack, restRule, stackSymbol :: collect)
                    } else {
                      throw new RuntimeException(
                        "Should be unreachable, rule and stack symbols do not match"
                      )
                    }
                  case _ =>
                    throw new RuntimeException(
                      "Should be unreachable, stack has incorrect structure"
                    )
                }
              case _ => (stack, Node(reducingRule, collect)) // empty rule
            }
          }

          val (newStack, tree) = take(Right(state) :: stack, rhs.reverse, Nil)
          val prevState = newStack.head.toOption.get
          val nextState = table.nonTerminalPart((prevState, lhs))
          val nextStack = Left(tree) :: newStack
          Left(ParserState(table, nextState, nextStack, lookahead, input))
      }

    @tailrec final def eval: Tree = next match {
      case Right(value) => value
      case Left(state)  => state.eval
    }
  }

  private object ParserState {
    def apply(
        table: Table,
        start: MultiState,
        input: List[Token]
    ): ParserState =
      ParserState(table, start, Nil, input.head, input.tail)
  }
  def parse(list: List[Token]): Tree =
    ParserState(table, startState, Nil, list.head, list.tail).eval
}
