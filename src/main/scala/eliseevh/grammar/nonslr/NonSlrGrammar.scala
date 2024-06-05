package eliseevh.grammar.nonslr

import eliseevh.parsergen.grammar.Grammar

import java.nio.file.Path

object NonSlrGrammar {
  import eliseevh.parsergen.syntax.GrammarSyntax._
  val grammar: Grammar = for {
    semi <- terminal";"
    eq <- terminal"="
    id <- terminal"id"
    add <- terminal"+"
    s <- nonTerminal"S"
    l <- nonTerminal"L"
    r <- nonTerminal"R"
    l1 <- nonTerminal"L1"
    _ <- semi --> ";"
    _ <- eq --> "="
    _ <- id --> "a"
    _ <- add --> "\\+"
    _ <- withNoConversion(s --> l1 ~: eq ~: r ~: end[Unit])
    _ <- withNoConversion(s --> r)
    _ <- withNoConversion(l --> id)
    _ <- withNoConversion(l1 --> l)
    _ <- withNoConversion(l --> add ~: r ~: end[Unit])
    _ <- withNoConversion(r --> l)
  } yield s

  def main(args: Array[String]): Unit = {
    grammar withName "NonSlr" inPackage "eliseevh.nonslr" generateIn Path.of("src", "main", "scala")
  }
}
