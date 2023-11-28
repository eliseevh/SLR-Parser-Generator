package eliseevh.parsergen.grammar.lexer

import scala.util.matching.Regex

trait TerminalRule[R] {
  def tryMatch(input: String): Option[Either[String, Token[R]]]
}

object TerminalRule {
  def apply[R](
      descriptor: TerminalDescriptor[R],
      regex: Regex,
      conversion: String => R
  ): NormalTerminalRule[R] = NormalTerminalRule(descriptor, regex, conversion)
}

case class NormalTerminalRule[R](
    descriptor: TerminalDescriptor[R],
    regex: Regex,
    conversion: String => R
) extends TerminalRule[R] {
  override def tryMatch(input: String): Option[Either[String, Token[R]]] =
    regex
      .findPrefixMatchOf(input)
      .map(matched =>
        Right(Token(conversion(matched.matched), matched.matched, descriptor))
      )
}

case class SkipTerminalRule(regex: Regex) extends TerminalRule[Nothing] {
  override def tryMatch(input: String): Option[Either[String, Token[Nothing]]] =
    regex.findPrefixMatchOf(input).map(matched => Left(matched.matched))
}
