package eliseevh.parsergen.runtime

import eliseevh.parsergen.grammar.lexer.TerminalDescriptor.Eof
import eliseevh.parsergen.grammar.lexer.{TerminalRule, Token}

import scala.annotation.tailrec

case class Lexer(rules: List[TerminalRule[?]]) {
  def tokenize(input: String): List[Token[?]] = {
    @tailrec
    def loop(restInput: String, tokenized: List[Token[?]]): List[Token[?]] =
      if (restInput.isEmpty) {
        (Token((), "", Eof) :: tokenized).reverse
      } else {
        tryMatch(restInput) match {
          case Left(rest) =>
            if (rest.isEmpty) {
              (Token((), "", Eof) :: tokenized).reverse
            } else {
              throw new RuntimeException(s"Cannot tokenize '$restInput'")
            }
          case Right((token, rest)) =>
            loop(rest, token :: tokenized)
        }
      }
    loop(input, Nil)
  }

  @tailrec
  private def tryMatch(input: String): Either[String, (Token[?], String)] =
    rules
      .map(_.tryMatch(input))
      .collect { case Some(v) => v }
      .reduceOption((token1, token2) =>
        if (getTextLength(token1) < getTextLength(token2)) token2 else token1
      ) match {
      case Some(Left(skip)) => tryMatch(input.substring(skip.length))
      case Some(Right(result)) =>
        Right((result, input.substring(result.source.length)))
      case None => Left(input)
    }

  private def getTextLength(token: Either[String, Token[?]]): Int =
    token match {
      case Left(text)               => text.length
      case Right(Token(_, text, _)) => text.length
    }
}
