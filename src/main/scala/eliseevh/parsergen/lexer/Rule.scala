package eliseevh.parsergen.lexer

import eliseevh.parsergen.grammar.NormalTerminal

import scala.util.matching.Regex

case class Rule(regex: Regex, terminal: NormalTerminal) {
  def tryMatch(input: String): Option[Token] = regex
    .findPrefixMatchOf(input)
    .map(matched => Token(matched.matched, terminal))
}
