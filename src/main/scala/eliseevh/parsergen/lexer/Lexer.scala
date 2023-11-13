package eliseevh.parsergen.lexer

import eliseevh.parsergen.grammar.Eof

import scala.annotation.tailrec

case class Lexer(grammar: Grammar) {
  def tokenize(input: String): List[Token] = {
    @tailrec
    def loop(restInput: String, tokenized: List[Token]): List[Token] = if (
      restInput.isEmpty
    ) {
      (Token("", Eof) :: tokenized).reverse
    } else {
      grammar.tryMatch(restInput) match {
        case None => throw new RuntimeException(s"Cannot tokenize '$restInput'")
        case Some(token) =>
          loop(restInput.substring(token.text.length), token :: tokenized)
      }
    }
    loop(input, Nil)
  }
}
