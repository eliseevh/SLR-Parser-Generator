package eliseevh.parsergen.lexer

case class Grammar(rules: Set[Rule]) {
  def tryMatch(input: String): Option[Token] =
    rules
      .map(_.tryMatch(input))
      .collect { case Some(v) => v }
      .reduceOption((token1, token2) =>
        if (token1.text.length < token2.text.length) token2 else token1
      )
}
