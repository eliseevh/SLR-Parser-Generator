import eliseevh.calculator.generated.parseExpression
import eliseevh.goodlang.generated.parseGoodLang

object Examples {
  def main(args: Array[String]): Unit = {
    val exprExample = parseExpression(" 1 + 2 * (3 / 4.0/2-(3  *3)) ")
    val programExample = parseGoodLang(
      "fun func(x, y) {\n  return x + y - x and y;\n}\nlet a = 7;\nlet b = -3;\na = a and b or func(a, a - b);\nprintln(a);"
    )
    println(exprExample)
    println()
    println(programExample)
  }
}
