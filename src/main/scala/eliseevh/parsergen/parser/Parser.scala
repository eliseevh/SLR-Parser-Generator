package eliseevh.parsergen.parser

import eliseevh.parsergen.generic.TerminalDescriptor.Eof
import eliseevh.parsergen.generic.grammar.NonTerminalRule
import eliseevh.parsergen.generic.lexer.Token
import eliseevh.parsergen.generic.{ElementDescriptor, TerminalDescriptor}
import eliseevh.parsergen.grammar._
import eliseevh.parsergen.parser.state.MultiState
import eliseevh.parsergen.result._

import scala.annotation.tailrec

case class Parser(table: Table, startState: MultiState) {
  private case class ParserState(
      table: Table,
      state: MultiState,
      stack: List[Either[Tree[?], MultiState]],
      lookahead: Token[?],
      input: List[Token[?]]
  ) {
    private def next: Either[ParserState, Tree[?]] =
      table.terminalPart.get((state, lookahead.descriptor)) match {
        case None =>
          throw new ParserException(
            s"Unexpected terminal: '${lookahead.descriptor.name}'(value=\"${lookahead.value}\", source=\"${lookahead.source}\")"
          )
        case Some(Accept) =>
          stack match {
            case List(Left(result), Right(`startState`)) => Right(result)
            case _ =>
              throw new ParserException(
                "Incorrect stack at the end of the input"
              )
          }
        case Some(Shift(nextState)) =>
          lookahead.descriptor match {
            case Eof => throw new ParserException("Unexpected Eof")
            case _: TerminalDescriptor[?] =>
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
        case Some(
              Reduce(reducingRule @ NonTerminalRule(lhs, rhs, conversion))
            ) =>
          @tailrec def take(
              stack: List[Either[Tree[?], MultiState]],
              rule: Seq[ElementDescriptor[?]],
              collect: List[Tree[?]]
          ): (List[Either[Tree[?], MultiState]], Tree[?]) = {
            rule match {
              case symbol :: restRule =>
                stack match {
                  case Right(_) :: Left(stackSymbol) :: restStack =>
                    if (stackSymbol.descriptor == symbol) {
                      take(restStack, restRule, stackSymbol :: collect)
                    } else {
                      throw new AssertionError(
                        "Should be unreachable, rule and stack symbols do not match"
                      )
                    }
                  case _ =>
                    throw new AssertionError(
                      "Should be unreachable, stack has incorrect structure"
                    )
                }
              case _ =>
                (
                  stack,
                  Node(reducingRule, collect, conversion(collect.map(_.value)))
                ) // empty rule
            }
          }

          val (newStack, tree) = take(Right(state) :: stack, rhs.reverse, Nil)
          val prevState = newStack.head.toOption.get
          val nextState = table.nonTerminalPart.get((prevState, lhs))
          val nextStack = Left(tree) :: newStack
          nextState match {
            case None => throw new ParserException(
              s"Unexpected terminal: '${lookahead.descriptor.name}'(" +
                s"value=\"${lookahead.value}\", " +
                s"source=\"${lookahead.source}\")")
            case Some(state) =>
              Left(ParserState(table, state, nextStack, lookahead, input))
          }

      }

    @tailrec final def eval: Tree[?] = next match {
      case Right(value) => value
      case Left(state)  => state.eval
    }
  }

  private object ParserState {
    def apply(
        table: Table,
        start: MultiState,
        input: List[Token[?]]
    ): ParserState =
      ParserState(table, start, Nil, input.head, input.tail)
  }
  def parse(list: List[Token[?]]): Tree[?] =
    ParserState(table, startState, Nil, list.head, list.tail).eval
}
