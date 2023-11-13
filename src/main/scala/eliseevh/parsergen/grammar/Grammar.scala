package eliseevh.parsergen.grammar

case class Grammar(
    start: NormalNonTerminal,
    rules: Set[Rule],
    nonTerminals: Set[NonTerminal],
    terminals: Set[Terminal]
)
