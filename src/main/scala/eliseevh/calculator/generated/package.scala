package eliseevh.calculator

import java.io.{ObjectInputStream, ByteArrayInputStream}
import java.util.zip.GZIPInputStream
import java.util.Base64

package object generated {
    sealed trait ExpressionNode

    final case class `/`(value: eliseevh.grammar.calculator.ExpressionCalculatorGrammar.Op) extends ExpressionNode
    final case class `)`(value: String) extends ExpressionNode
    final case class `*`(value: eliseevh.grammar.calculator.ExpressionCalculatorGrammar.Op) extends ExpressionNode
    final case class `-`(value: eliseevh.grammar.calculator.ExpressionCalculatorGrammar.Op) extends ExpressionNode
    final case class `+`(value: eliseevh.grammar.calculator.ExpressionCalculatorGrammar.Op) extends ExpressionNode
    final case class `number`(value: Double) extends ExpressionNode
    final case class `(`(value: String) extends ExpressionNode


    final case class `F`(children: `FChildren`, value: Double) extends ExpressionNode
    sealed trait `FChildren`
    final case class `F->number`(`number0`: `number`) extends `FChildren`
    final case class `F->(E)`(`(0`: `(`, `E1`: `E`, `)2`: `)`) extends `FChildren`

    final case class `T`(children: `TChildren`, value: Double) extends ExpressionNode
    sealed trait `TChildren`
    final case class `T->T*F`(`T0`: `T`, `*1`: `*`, `F2`: `F`) extends `TChildren`
    final case class `T->T/F`(`T0`: `T`, `/1`: `/`, `F2`: `F`) extends `TChildren`
    final case class `T->F`(`F0`: `F`) extends `TChildren`

    final case class `E`(children: `EChildren`, value: Double) extends ExpressionNode
    sealed trait `EChildren`
    final case class `E->E+T`(`E0`: `E`, `+1`: `+`, `T2`: `T`) extends `EChildren`
    final case class `E->T`(`T0`: `T`) extends `EChildren`
    final case class `E->E-T`(`E0`: `E`, `-1`: `-`, `T2`: `T`) extends `EChildren`


    private def fromTree(tree: eliseevh.parsergen.runtime.Tree[?]): ExpressionNode = tree match {
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "/" => `/`(token.value.asInstanceOf[eliseevh.grammar.calculator.ExpressionCalculatorGrammar.Op])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "number" => `number`(token.value.asInstanceOf[Double])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "(" => `(`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "-" => `-`(token.value.asInstanceOf[eliseevh.grammar.calculator.ExpressionCalculatorGrammar.Op])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == ")" => `)`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "*" => `*`(token.value.asInstanceOf[eliseevh.grammar.calculator.ExpressionCalculatorGrammar.Op])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "+" => `+`(token.value.asInstanceOf[eliseevh.grammar.calculator.ExpressionCalculatorGrammar.Op])

        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "E" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("E"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("+"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("T")) => children match {
            case List(v0, v1, v2) => `E`(`E->E+T`(fromTree(v0).asInstanceOf[`E`], fromTree(v1).asInstanceOf[`+`], fromTree(v2).asInstanceOf[`T`]), value.asInstanceOf[Double])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "T" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("F")) => children match {
            case List(v0) => `T`(`T->F`(fromTree(v0).asInstanceOf[`F`]), value.asInstanceOf[Double])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "T" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("T"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("*"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("F")) => children match {
            case List(v0, v1, v2) => `T`(`T->T*F`(fromTree(v0).asInstanceOf[`T`], fromTree(v1).asInstanceOf[`*`], fromTree(v2).asInstanceOf[`F`]), value.asInstanceOf[Double])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "T" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("T"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("/"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("F")) => children match {
            case List(v0, v1, v2) => `T`(`T->T/F`(fromTree(v0).asInstanceOf[`T`], fromTree(v1).asInstanceOf[`/`], fromTree(v2).asInstanceOf[`F`]), value.asInstanceOf[Double])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "E" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("E"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("-"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("T")) => children match {
            case List(v0, v1, v2) => `E`(`E->E-T`(fromTree(v0).asInstanceOf[`E`], fromTree(v1).asInstanceOf[`-`], fromTree(v2).asInstanceOf[`T`]), value.asInstanceOf[Double])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "F" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("number")) => children match {
            case List(v0) => `F`(`F->number`(fromTree(v0).asInstanceOf[`number`]), value.asInstanceOf[Double])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "E" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("T")) => children match {
            case List(v0) => `E`(`E->T`(fromTree(v0).asInstanceOf[`T`]), value.asInstanceOf[Double])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "F" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("("), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("E"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(")")) => children match {
            case List(v0, v1, v2) => `F`(`F->(E)`(fromTree(v0).asInstanceOf[`(`], fromTree(v1).asInstanceOf[`E`], fromTree(v2).asInstanceOf[`)`]), value.asInstanceOf[Double])
        }
    }


    private val parser: eliseevh.parsergen.runtime.Parser = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(Base64.getDecoder.decode("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")))).readObject().asInstanceOf[eliseevh.parsergen.runtime.Parser]
    private val lexer: eliseevh.parsergen.runtime.Lexer = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(Base64.getDecoder.decode("H4sIAAAAAAAA/61Wz28bRRgd27FJkxIoEZQ2FCXVprKTZheHAhU5IJQ0YLFJqjpCiBiJiXdsb7I7u5mddTZQcUNcOXCFHrlw4cQN+AsQl/4DvVdC4gQX+GZmfzheN0HFexjtzsx+78373jczPzxG5YCheeLYASH9nu5jFhDWJVRnIeW2S3STRIS9/+f33v0PHz0oooKJyix0SMDRghm0sYONtuc4pM1tjxq264Yc7zvEMO2Ar0U+BF+Vs/Rslg7hCbPb+gbp4NDhTfjAjv0ZFoN3mRedIPWUSgLumQ5uc4+dcHQ1D7ipxhTSUg6pwQkTdOJp2q4XvyUIxdMI1/MIQyEU0g2FlGi05VkgyRPXUZAo066cte7gAMS7ZB7gPjYcTLuG7ILAfchEbgmppLqQVMuIo8g/nvgHHuDz+ogEdhl2Xcx0RyRQ3/aYi51dwlybYuceEJnsHf38699/TRZRyURTbY/2CQsAUXBTKmyGVHKor8EEiwRtZvugAUdvmAmekeIZMZ4h8YwEaSP9bU04h3RJxNFcDBBy2zFczNs9G2S4JwaVvgtCHF2Io9u07x0SPRGXWCZ29y3s4d+/uVl788simmqgGdv1nS3Ce571gU2tPXSxjX0eMmK9y7og9uzegNo7+wcgLbCZUZMAWibgCH2Bpkz0cideNXYaFHIP3khy9sJAlCYXP0KUayPmKyrb2CUi6Ismuv7ESU27S7GgGs+8IJaS8oGOgbUNBJzNeocjvGTTgGMwJubEUlN2T3w1GvngnUrIEkWUxLEiX//20YPng5ojrSVMBn68nRorsRMkrh06GBKq34l8RgLhmfW08z01LfGpeCKfo+eGHMVRGfu+AyV3o5rPTS3fxTMqqdMyKsZZVCBxGqYehRxo8a/abY4Wq/l01oacv7omZJv/TxWWd7w7vVHa+un+MVTYx6hCZQWaaIJmaZzkkJk0rZFf4KiwzNHbT6m6viOK54raQ0Rx6Ulx6bK4Hj1+ePDdHwu/FFERdj0fc7Ai1PsVJYSsRlmhxl01BPZelME0MaYlwTQZTAMxvdAX7KE25s86DJrkSNX1ZWk6yUwC6THQZvfhIW0UnwViDVTuOLgbZAQz5yKOiq1lWRkl2VZEM308IW0me2ZkOyttLt4uiyHxclU0c6J5RTTXRPMqbA05a9TrMucq8aJdEFlZ+X9ZEXEWZVtT6yisiI+b4+J8axTnpTFzLraWxkr6rVGkjXELbZzDGXa56ojiDk4ox5Eeh27Kr9O72tA6eRZm4FRUYYzTYeAETNU4tnlv21tPj1+tPrQnpltTrkvANjPlqhxV1Mio1FUHZBDNVvZj7cwfa0+f87lczldXNWzBsUus81cZ7/wiFknJVmjo7hO4g1Q2vBD2ljzj8so7LWt5rKRvJaQll7Tp5MGnJHhLTxkwVD/35Gge2v7gzezzH7997dOvdqz4ti02ShHsUjTC3hf35lu0xVus1flkYNEX+ukldcTFO71M3aHW6Rtl9C8id4IJEgwAAA==")))).readObject().asInstanceOf[eliseevh.parsergen.runtime.Lexer]

    def parseExpression(input: String): `E` = fromTree(parser.parse(lexer.tokenize(input))).asInstanceOf[`E`]
}
