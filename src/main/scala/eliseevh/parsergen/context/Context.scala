package eliseevh.parsergen.context

import eliseevh.parsergen.grammar.{Grammar, GrammarSymbol, NormalNonTerminal, NormalTerminal, Rule}
import eliseevh.parsergen.parser.Worker

import java.io.{ByteArrayOutputStream, ObjectOutputStream}
import java.nio.file.{Files, Path}
import java.util.Base64
import scala.util.Try

case class Context(grammarName: String, packageName: String, grammar: Grammar, lexerGrammar: eliseevh.parsergen.lexer.Grammar) {
  private val LS: String = System.lineSeparator()
  def generate(root: Path): Try[Unit] = {
    val worker = Worker(grammar, lexerGrammar)

    val preParserBytesWriter = new ByteArrayOutputStream()
    new ObjectOutputStream(preParserBytesWriter).writeObject(worker.preParser)
    val lexerBytesWriter = new ByteArrayOutputStream()
    new ObjectOutputStream(lexerBytesWriter).writeObject(worker.lexer)
    val preParserBytes = Base64.getEncoder.encodeToString(preParserBytesWriter.toByteArray)
    val lexerBytes = Base64.getEncoder.encodeToString(lexerBytesWriter.toByteArray)

    val nodeClassName = s"${grammarName}Node"

    val packageDefinition =
      Vector("package ", packageName, LS)
    val imports =
      Vector("import java.io.{ObjectInputStream, ByteArrayInputStream}", LS, "import java.util.Base64", LS)
    def packageObjectDefinition(innerPart: Vector[String]): Vector[String] = {
      innerPart
        .prepended(
          s"package object generated {${LS}sealed trait $nodeClassName$LS"
          )
        .appended(s"$LS}$LS")
    }

    val terminals = grammar.terminals.view
                           .collect { case NormalTerminal(name) =>
                             s"final case class `$name`(value: String) extends $nodeClassName"
                           }
                           .flatMap(Seq(_, LS))
                           .toVector

    val ruleElementToString: PartialFunction[(GrammarSymbol, Int), String] = {
      case (NormalNonTerminal(name), i) => s"`$name$i`: `$name`"
      case (NormalTerminal(name), i)    => s"`$name$i`: `$name`"
    }

    val nonTerminals: Vector[String] = grammar.nonTerminals.view
                                              .collect { case nt @ NormalNonTerminal(name) =>
                                                val childrenClassName = s"`${name}Children`"
                                                Vector(
                                                  s"final case class `$name`(children: $childrenClassName) extends $nodeClassName",
                                                  LS,
                                                  s"sealed trait $childrenClassName",
                                                  LS
                                                  ) ++ grammar.rules.collect {
                                                  case rule @ Rule(lhs, rhs) if lhs == nt =>
                                                    val ruleId = worker.mapping.rules(rule)
                                                    s"final case class `${name}Rule$ruleId`(${rhs.zipWithIndex.collect(ruleElementToString).mkString(", ")}) extends $childrenClassName$LS"
                                                }

                                              }
                                              .flatten
                                              .toVector

    val preParser: Vector[String] = Vector(
      s"private val parser: eliseevh.parsergen.parser.Parser = new ObjectInputStream(new ByteArrayInputStream(Base64.getDecoder.decode(\"$preParserBytes\"))).readObject().asInstanceOf[eliseevh.parsergen.parser.Parser]$LS"
      )
    val lexer: Vector[String] = Vector(
      s"private val lexer: eliseevh.parsergen.lexer.Lexer = new ObjectInputStream(new ByteArrayInputStream(Base64.getDecoder.decode(\"$lexerBytes\"))).readObject().asInstanceOf[eliseevh.parsergen.lexer.Lexer]$LS"
      )
    val fromTree: Vector[String] =
      (grammar.terminals.collect { case NormalTerminal(name) =>
        s"case eliseevh.parsergen.result.Leaf(terminal) if terminal.terminal == eliseevh.parsergen.grammar.Terminal(\"$name\") => `$name`(terminal.text)$LS"
      }.toVector ++ grammar.rules.collect {
        case rule @ Rule(NormalNonTerminal(lhsName), rightSide) =>
          val collectedRightSide = rightSide.collect {
            case NormalTerminal(name) =>
              s"eliseevh.parsergen.grammar.Terminal(\"$name\")"
            case NormalNonTerminal(name) =>
              s"eliseevh.parsergen.grammar.NonTerminal(\"$name\")"
          }
          s"case eliseevh.parsergen.result.Node(rule, children) if rule == eliseevh.parsergen.grammar.Rule(eliseevh.parsergen.grammar.NonTerminal(\"$lhsName\") -> List(${collectedRightSide
            .mkString(", ")})) => children match {${LS}case List(${collectedRightSide.view.zipWithIndex
                                                                                     .map(value => s"v${value._2}")
                                                                                     .mkString(", ")}) => `$lhsName`(`${lhsName}Rule${worker.mapping
                                                                                                                                            .rules(rule)}`(${rightSide.view.collect {
                                                                                                                                                                        case NormalTerminal(name) => name
                                                                                                                                                                        case NormalNonTerminal(name) => name}
                                                                                                                                                                      .zipWithIndex
                                                                                                                                                                      .map { case (name, idx) =>
                                                                                                                                                                        s"fromTree(v$idx).asInstanceOf[`$name`]"
                                                                                                                                                                      }
                                                                                                                                                                      .mkString(", ")}))}$LS"

      }.toVector)
        .prepended(
          s"private def fromTree(tree: eliseevh.parsergen.result.Tree): $nodeClassName = tree match {$LS"
          )
        .appended(s"}$LS")

    val parseMethodDeclaration: Vector[String] = Vector(
      s"def parse$grammarName(input: String): `${grammar.start.name}` = fromTree(parser.parse(lexer.tokenize(input))).asInstanceOf[`${grammar.start.name}`]"
      )
    val generatedFileContent: Vector[String] =
      packageDefinition ++ imports ++ packageObjectDefinition(
        terminals ++ nonTerminals ++ preParser ++ lexer ++ fromTree ++ parseMethodDeclaration
        )

    Try {
      val fullPackageName = s"$packageName.generated".split('.')
      val directory = Files.createDirectories(
        root.resolve(Path.of(fullPackageName.head, fullPackageName.tail: _*))
        )

      Files.writeString(
        directory.resolve("package.scala"),
        generatedFileContent.mkString
        )
    }
  }

  def unicodeEscape(ch: Char): String = {
    String.format("\\u%04x", ch.asInstanceOf[Int])
//    val hexString = ch.toInt.toHexString
//    "\\u" + "0".repeat(Math.max(0, 4 - hexString.length)) + hexString
  }
}
