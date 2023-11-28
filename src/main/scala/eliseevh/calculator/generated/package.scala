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
