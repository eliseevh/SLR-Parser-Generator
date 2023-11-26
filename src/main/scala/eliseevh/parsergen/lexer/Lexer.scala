package eliseevh.parsergen.lexer

import eliseevh.parsergen.generic.TerminalDescriptor.Eof
import eliseevh.parsergen.generic.lexer.Token

import scala.annotation.tailrec

case class Lexer(grammarI: eliseevh.parsergen.grammar.Grammar) {
  private val grammar: Grammar = Grammar(grammarI.terminalRules.toList)
  def tokenize(input: String): List[Token[?]] = {
    @tailrec
    def loop(restInput: String, tokenized: List[Token[?]]): List[Token[?]] =
      if (restInput.isEmpty) {
        (Token((), "", Eof) :: tokenized).reverse
      } else {
        grammar.tryMatch(restInput) match {
          case None =>
            throw new RuntimeException(s"Cannot tokenize '$restInput'")
          case Some((token, rest)) =>
            loop(rest, token :: tokenized)
        }
      }
    loop(input, Nil)
  }
}
