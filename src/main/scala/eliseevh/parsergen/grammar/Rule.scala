package eliseevh.parsergen.grammar

case class Rule(leftSide: NonTerminal, rightSide: List[GrammarSymbol]) {
  override def toString: String = s"$leftSide -> ${rightSide.mkString(" ")}"
}

object Rule {
  def apply(rule: (NonTerminal, List[GrammarSymbol])): Rule = rule match {
    case (leftSide, rightSide) => Rule(leftSide, rightSide)
  }
}
