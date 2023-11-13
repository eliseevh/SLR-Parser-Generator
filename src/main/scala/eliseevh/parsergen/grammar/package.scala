package eliseevh.parsergen

package object grammar {
  sealed trait GrammarSymbol extends Serializable

  sealed trait Terminal extends GrammarSymbol
  object Terminal {
    def apply(name: String): NormalTerminal = NormalTerminal(name)
  }
  final case class NormalTerminal(name: String) extends Terminal {
    override def toString: String = s"'$name'"
  }
  final object Eof extends Terminal {
    override def toString: String = "$"
  }

  sealed trait NonTerminal extends GrammarSymbol
  object NonTerminal {
    def apply(name: String): NormalNonTerminal = NormalNonTerminal(name)
  }
  final case class NormalNonTerminal(name: String) extends NonTerminal {
    override def toString: String = name
  }
  final object ExtraStarting extends NonTerminal {
    override def toString: String = "ExtraStarting"
  }

  implicit class StringSyntax(val stringContext: StringContext) extends AnyVal {
    def t(args: Any*): NormalTerminal = Terminal(stringContext.s(args: _*))
    def nt(args: Any*): NormalNonTerminal = NonTerminal(
      stringContext.s(args: _*)
    )
  }
}
