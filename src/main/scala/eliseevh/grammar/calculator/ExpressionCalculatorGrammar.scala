package eliseevh.grammar.calculator

import eliseevh.parsergen.grammar.Grammar
import java.nio.file.Path

object ExpressionCalculatorGrammar {
  import eliseevh.parsergen.syntax.GrammarSyntax._
  type Op = (Double, Double) => Double
  val conversion: Double => Op => Double => Double = l => op => r => op(l, r)
  val grammar: Grammar = for {
    plus <- terminal"+" withValue ofType[Op]
    minus <- terminal"-" withValue ofType[Op]
    mul <- terminal"*" withValue ofType[Op]
    div <- terminal"/" withValue ofType[Op]
    lparen <- terminal"("
    rparen <- terminal")"
    number <- terminal"number" withValue ofType[Double]

    _ <- plus --> "\\+" withConversion {_ => {_ + _}}
    _ <- minus --> "-" withConversion {_ => {_ - _}}
    _ <- mul --> "\\*" withConversion {_ => {_ * _}}
    _ <- div --> "/" withConversion {_ => {_ / _}}
    _ <- lparen --> "\\("
    _ <- rparen --> "\\)"
    _ <- number --> "-?\\d+" withConversion {_.toDouble}
    _ <- number --> "-?\\d+\\.\\d+" withConversion {_.toDouble}
    _ <- skip("[ \\n\\t\\r\\f]+")

    e <- nonTerminal"E" withValue ofType[Double]
    t <- nonTerminal"T" withValue ofType[Double]
    f <- nonTerminal"F" withValue ofType[Double]

    _ <- e --> e ~: plus ~: t ~: end[Double] withConversion conversion
    _ <- e --> e ~: minus ~: t ~: end[Double] withConversion conversion
    _ <- e --> t withConversion identity
    _ <- t --> t ~: mul ~: f ~: end[Double] withConversion conversion
    _ <- t --> t ~: div ~: f ~: end[Double] withConversion conversion
    _ <- t --> f withConversion identity
    _ <- f --> lparen ~: e ~: rparen ~: end[Double] withConversion {_ => v => _ => v}
    _ <- f --> number withConversion identity
  } yield e

  def main(args: Array[String]): Unit = {
    grammar withName "Expression" inPackage "eliseevh.calculator" generateIn Path.of("src", "main", "scala")
  }
}
