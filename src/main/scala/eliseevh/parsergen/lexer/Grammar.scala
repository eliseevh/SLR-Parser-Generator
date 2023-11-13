package eliseevh.parsergen.lexer

case class Grammar(rules: List[Rule]) {
  def tryMatch(input: String): Option[(Token, String)] =
    rules
      .map(_.tryMatch(input))
      .collect { case Some(v) => v }
      .reduceOption((token1, token2) =>
        if (getTextLength(token1) < getTextLength(token2)) token2 else token1
      ) match {
      case Some(Left(skip))    => tryMatch(input.substring(skip.length))
      case Some(Right(result)) => Some((result, input.substring(result.text.length)))
      case None                => None
    }

  private def getTextLength(token: Either[String, Token]): Int = token match {
    case Left(text)            => text.length
    case Right(Token(text, _)) => text.length
  }
}
