package eliseevh

import eliseevh.parsergen.context.Context

import java.nio.file.Path
import scala.util.Try

package object parsergen {
  def terminals(rules: LexerRule*): eliseevh.parsergen.lexer.Grammar = {
    eliseevh.parsergen.lexer.Grammar(rules.map(_.toRule).toSet)
  }

  def nonTerminals(
      startRule: String,
      rules: GrammarRule*
  ): eliseevh.parsergen.lexer.Grammar => eliseevh.parsergen.grammar.Grammar =
    lexerGrammar => {
      val terminals = lexerGrammar.rules.map(_.terminal)
      val terminalNames = terminals.map(_.name)
      eliseevh.parsergen.grammar.Grammar(
        eliseevh.parsergen.grammar.NonTerminal(startRule),
        rules.map { case GrammarRule(lhs, rhs) =>
          eliseevh.parsergen.grammar.Rule(
            lhs,
            rhs.map(name =>
              if (terminalNames.contains(name))
                eliseevh.parsergen.grammar.Terminal(name)
              else eliseevh.parsergen.grammar.NonTerminal(name)
            )
          )
        }.toSet,
        rules.map(_.lhs).toSet,
        terminals.toSet + eliseevh.parsergen.grammar.Eof
      )
    }

  case class GrammarRule(
      lhs: eliseevh.parsergen.grammar.NormalNonTerminal,
      rhs: List[String]
  )
  implicit class RuleLhs(val name: String) extends AnyVal {
    def -->(rhs: RuleRhs): GrammarRule =
      GrammarRule(
        eliseevh.parsergen.grammar.NonTerminal(name),
        rhs.values
      )
    def -->(rhs: String): GrammarRule =
      this --> RuleRhs(List(rhs))
    def --> : GrammarRule = this --> RuleRhs(Nil)
  }

  implicit class RuleRhsPart(val name: String) extends AnyVal {
    def ~:(single: RuleRhsPart): RuleRhs = RuleRhs(List(single.name, name))
  }

  case class RuleRhs(values: List[String]) {
    def ~:(element: RuleRhsPart): RuleRhs = RuleRhs(element.name :: values)
  }

  object EMPTY_RULE extends RuleRhs(Nil)

  implicit class LexerRule(val pair: (String, String)) extends AnyVal {
    def toRule: eliseevh.parsergen.lexer.Rule = eliseevh.parsergen.lexer
      .Rule(pair._1.r, eliseevh.parsergen.grammar.Terminal(pair._2))
  }

  class GrammarDSL(
      val lexer: eliseevh.parsergen.lexer.Grammar,
      val parser: eliseevh.parsergen.grammar.Grammar
  ) {
    def this(lexer: eliseevh.parsergen.lexer.Grammar)(
        parser: eliseevh.parsergen.lexer.Grammar => eliseevh.parsergen.grammar.Grammar
    ) = this(lexer, parser(lexer))

    def generate(root: Path): Try[Unit] = {
      Context(
        this.getClass.getSimpleName,
        this.getClass.getPackageName,
        parser,
        lexer
      ).generate(root)
    }
  }
}
