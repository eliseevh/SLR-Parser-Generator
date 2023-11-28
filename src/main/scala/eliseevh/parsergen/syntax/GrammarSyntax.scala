package eliseevh.parsergen.syntax

import eliseevh.parsergen
import eliseevh.parsergen.generator.Generator
import eliseevh.parsergen.grammar.lexer.{SkipTerminalRule, TerminalDescriptor, TerminalRule}
import eliseevh.parsergen.grammar.parser.{NonTerminalDescriptor, NonTerminalRule}
import eliseevh.parsergen.grammar.{ElementDescriptor, Grammar}

import scala.language.implicitConversions
import scala.reflect.runtime.universe._
import scala.util.matching.Regex

object GrammarSyntax {
  class IncorrectGrammarException(message: String)
      extends RuntimeException(message)
  case class NamedGrammar(
      grammar: Grammar,
      name: String
  ) {
    def inPackage(packageName: String): Generator =
      Generator(name, packageName, grammar)
  }

  implicit class GrammarWrapper(grammar: Grammar) {
    def withName(name: String): NamedGrammar = NamedGrammar(grammar, name)
  }

  def ofType[T: TypeTag]: TypeDesc[T] = TypeDesc()
  implicit class StringSyntax(val stringContext: StringContext) {
    def terminal(args: Any*): TerminalDescriptor[String] =
      TerminalDescriptor(stringContext.s(args: _*))

    def nonTerminal(args: Any*): NonTerminalDescriptor[Unit] =
      NonTerminalDescriptor(stringContext.s(args: _*))
  }

  implicit def extractGrammar[R](
      state: GrammarBuilderState[NonTerminalDescriptor[R]]
  ): Grammar = {
    val (grammar, start) = state.runState(GrammarBuilder.empty)
    if (
      grammar.nonTerminalRules.view
        .distinctBy(rule => (rule.descriptor, rule.rightElements))
        .size != grammar.nonTerminalRules.size
    ) {
      throw new IncorrectGrammarException("Rules must be distinct")
    }
    val named = grammar.nonTerminalDescriptors.map(
      _.name
    ) ++ grammar.terminalDescriptors.map(_.name)
    if (named.view.distinct.size != named.size) {
      throw new IncorrectGrammarException(
        "Terminals and non-terminals must all have different names"
      )
    }
    parsergen.grammar.Grammar(
      start,
      grammar.nonTerminalRules.toSet,
      grammar.terminalRules.reverse,
      grammar.nonTerminalDescriptors.toSet,
      grammar.terminalDescriptors.toSet
    )
  }

  private def modifyState(
      f: GrammarBuilder => GrammarBuilder
  ): GrammarBuilderState[Unit] = GrammarBuilderState(state => (f(state), ()))

  implicit def save[R](
      descriptor: TerminalDescriptor[R]
  ): GrammarBuilderState[TerminalDescriptor[R]] =
    modifyState(_.addTerminalDescriptor(descriptor)).map(_ => descriptor)

  implicit def save[R](
      descriptor: NonTerminalDescriptor[R]
  ): GrammarBuilderState[NonTerminalDescriptor[R]] =
    modifyState(_.addNonTerminalDescriptor(descriptor)).map(_ => descriptor)

  implicit def save[R](
      rule: TerminalRule[R]
  ): GrammarBuilderState[TerminalRule[R]] =
    modifyState(_.addTerminalRule(rule)).map(_ => rule)

  implicit def save[R](
      rule: NonTerminalRule[R]
  ): GrammarBuilderState[NonTerminalRule[R]] =
    modifyState(_.addNonTerminalRule(rule)).map(_ => rule)

  implicit class TerminalDescriptorWrapper[R](
      descriptor: TerminalDescriptor[R]
  ) {
    def -->(implicit
        rhs: TerminalRuleMatcher
    ): TerminalRuleWithoutConversion[R] =
      TerminalRuleWithoutConversion(descriptor, rhs.get)
  }

  case class TerminalRuleWithoutConversion[R](
      descriptor: TerminalDescriptor[R],
      regex: Regex
  ) {
    def withConversion(conversion: String => R): TerminalRule[R] =
      TerminalRule(descriptor, regex, conversion)

  }
  def skip(
      regex: TerminalRuleMatcher
  ): GrammarBuilderState[TerminalRule[Nothing]] =
    save[Nothing](SkipTerminalRule(regex.get))

  implicit def withNoConversion(
      rule: TerminalRuleWithoutConversion[String]
  ): GrammarBuilderState[TerminalRule[String]] =
    TerminalRule(rule.descriptor, rule.regex, identity)

  sealed trait TerminalRuleMatcher {
    def get: Regex
  }

  implicit class RegexWrapper(regex: Regex) extends TerminalRuleMatcher {
    def withConversion[R](conversion: String => R): TerminalRuleRhs[R] =
      TerminalRuleRhs(regex, conversion)
    override def get: Regex = regex
  }

  implicit class StringWrapper(string: String) extends TerminalRuleMatcher {
    override def get: Regex = string.r
  }

  implicit class NonTerminalDescriptorWrapper[R](
      descriptor: NonTerminalDescriptor[R]
  ) {
    def -->[F](
        rhs: RuleRhsTyped[R, F]
    ): NonTerminalRuleWithoutConversion[R, F] =
      NonTerminalRuleWithoutConversion(descriptor, rhs)
  }

  implicit def withNoConversion[F](
      rule: NonTerminalRuleWithoutConversion[Unit, F]
  ): GrammarBuilderState[NonTerminalRule[Unit]] =
    NonTerminalRule(rule.descriptor, rule.names.toList, _ => ())

  def end[A]: RuleRhsTyped[A, A] = RuleRhsTyped.empty

  case class TerminalRuleRhs[R](regex: Regex, conversion: String => R)

  case class NonTerminalRuleWithoutConversion[R, F](
      descriptor: NonTerminalDescriptor[R],
      names: RuleRhsTyped[R, F]
  ) {
    def withConversion(conversion: F): NonTerminalRule[R] = {
      val v = names.withConversion(conversion)
      NonTerminalRule(descriptor, v.names, v.conversion)
    }
  }

  implicit class LastElementWrapper[E, R](element: ElementDescriptor[E])
      extends RuleRhsTyped[R, E => R] {
    override def withConversion(conversion: E => R): NonTerminalRuleRhs[R] =
      NonTerminalRuleRhs(
        List(element),
        { case List(v: E) =>
          conversion(v)
        }
      )

    override def toList: List[ElementDescriptor[_]] = List(element)
  }

  case class NonTerminalRuleRhs[R] private (
      names: List[ElementDescriptor[?]],
      conversion: List[Any] => R
  )

  sealed trait RuleRhsTyped[L, F] {
    import RuleRhsTyped._
    def ~:[E](element: ElementDescriptor[E]): RuleRhsTyped[L, E => F] =
      NonEmptyRuleRhsTyped(element, this)

    def withConversion(conversion: F): NonTerminalRuleRhs[L]

    def toList: List[ElementDescriptor[?]]
  }

  private object RuleRhsTyped {
    def empty[R]: RuleRhsTyped[R, R] = EmptyRuleRhsTyped()
    private case class EmptyRuleRhsTyped[R]() extends RuleRhsTyped[R, R] {
      override def toList: List[ElementDescriptor[_]] = Nil

      override def withConversion(conversion: R): NonTerminalRuleRhs[R] =
        NonTerminalRuleRhs(this.toList, { case Nil => conversion })
    }

    private case class NonEmptyRuleRhsTyped[L, E, I](
        desc: ElementDescriptor[E],
        rest: RuleRhsTyped[L, I]
    ) extends RuleRhsTyped[L, E => I] {
      override def toList: List[ElementDescriptor[?]] = desc :: rest.toList

      override def withConversion(conversion: E => I): NonTerminalRuleRhs[L] =
        NonTerminalRuleRhs(
          this.toList,
          { case (h: E) :: restArgs =>
            rest.withConversion(conversion(h)).conversion(restArgs)
          }
        )
    }
  }

  case class GrammarBuilderState[A](
      runState: GrammarBuilder => (GrammarBuilder, A)
  ) {
    def map[B](f: A => B): GrammarBuilderState[B] =
      GrammarBuilderState(state =>
        runState(state) match {
          case (newState, a) => (newState, f(a))
        }
      )

    def flatMap[B](f: A => GrammarBuilderState[B]): GrammarBuilderState[B] =
      GrammarBuilderState(state =>
        runState(state) match {
          case (newState, a) => f(a).runState(newState)
        }
      )
  }

  case class GrammarBuilder(
      terminalDescriptors: List[TerminalDescriptor[?]],
      nonTerminalDescriptors: List[NonTerminalDescriptor[?]],
      terminalRules: List[TerminalRule[?]],
      nonTerminalRules: List[NonTerminalRule[?]]
  ) {
    def addTerminalDescriptor(
        descriptor: TerminalDescriptor[?]
    ): GrammarBuilder = copy(
      terminalDescriptors = descriptor :: terminalDescriptors
    )

    def addNonTerminalDescriptor(
        descriptor: NonTerminalDescriptor[?]
    ): GrammarBuilder = copy(
      nonTerminalDescriptors = descriptor :: nonTerminalDescriptors
    )

    def addTerminalRule(rule: TerminalRule[?]): GrammarBuilder =
      copy(terminalRules = rule :: terminalRules)

    def addNonTerminalRule(rule: NonTerminalRule[?]): GrammarBuilder = copy(
      nonTerminalRules = rule :: nonTerminalRules
    )
  }

  private object GrammarBuilder {
    def empty: GrammarBuilder = GrammarBuilder(Nil, Nil, Nil, Nil)
  }
}
