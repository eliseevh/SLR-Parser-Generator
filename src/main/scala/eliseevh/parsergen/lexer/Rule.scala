package eliseevh.parsergen.lexer

import eliseevh.parsergen.grammar.NormalTerminal

import scala.util.matching.Regex

sealed trait Rule {
  def tryMatch(input: String): Option[Either[String, Token]]
}

case class NormalRule(regex: Regex, terminal: NormalTerminal) extends Rule {
  def tryMatch(input: String): Option[Right[Nothing, Token]] = regex
    .findPrefixMatchOf(input)
    .map(matched => Right(Token(matched.matched, terminal)))
}

case class SkipRule(regex: Regex) extends Rule {
  def tryMatch(input: String): Option[Left[String, Nothing]] =
    regex.findPrefixMatchOf(input).map(matched => Left(matched.matched))
}
