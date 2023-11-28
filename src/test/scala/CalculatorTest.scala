import eliseevh.calculator.generated.parseExpression

import scala.math.BigDecimal.double2bigDecimal
import scala.reflect.runtime.{currentMirror, universe}
import scala.tools.reflect.ToolBox
import scala.util.Random

object CalculatorTest {
  val toolbox: ToolBox[universe.type] = currentMirror.mkToolBox()
  def evalScala(expr: String): Double =
    toolbox.eval(toolbox.parse(expr)).asInstanceOf[Double]

  def main(args: Array[String]): Unit = {
    for { i <- Range.inclusive(1, 10) } {
      val expr = randomExpr()
      val trueValue = evalScala(expr)
      val parserValue = parseExpression(expr).value
      if (trueValue != parserValue) {
        println(s"Wrong value: $expr = $trueValue, not $parserValue")
        println("TESTS FAILED!")
        return
      }
      println(s"TEST#$i PASSED: $expr")
    }
    println("TESTS PASSED!")
  }
  def randomExpr(depth: Int = 10): String = {

    if (depth <= 0) {
      double2bigDecimal(
        Random.nextDouble() * 10000 - 5000
      ).bigDecimal.toPlainString
    } else {
      /*
        0 - plus
        1 - minus
        2 - mul
        3 - div
        4 - brackets
        5 - number
       */
      val variant = Random.nextInt(6)
      variant match {
        case 0 => randomExpr(depth - 1) ++ " + " ++ randomExpr(depth - 1)
        case 1 => randomExpr(depth - 1) ++ " - " ++ randomExpr(depth - 1)
        case 2 => randomExpr(depth - 1) ++ " * " ++ randomExpr(depth - 1)
        case 3 => randomExpr(depth - 1) ++ " / " ++ randomExpr(depth - 1)
        case 4 => "(" ++ randomExpr(depth - 1) ++ ")"
        case 5 =>
          double2bigDecimal(Random.nextDouble()).bigDecimal.toPlainString
      }
    }
  }
}
