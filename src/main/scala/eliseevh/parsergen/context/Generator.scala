package eliseevh.parsergen.context

import eliseevh.parsergen.generic.grammar.NonTerminalRule
import eliseevh.parsergen.generic.{ElementDescriptor, NonTerminalDescriptor, TerminalDescriptor}
import eliseevh.parsergen.grammar.Grammar
import eliseevh.parsergen.parser.Worker

import java.io.{ByteArrayOutputStream, ObjectOutputStream}
import java.nio.file.{Files, Path}
import java.util.Base64
import java.util.zip.GZIPOutputStream
import scala.util.{Try, Using}

case class Generator(grammarName: String, packageName: String, grammar: Grammar) {
  private val LS: String = System.lineSeparator()
  private val INDENT: String = "    "
  def generateIn(root: Path): Try[Unit] = {
    val worker = Worker(grammar)

    val preParserBytesWriter = new ByteArrayOutputStream()
    Using(new ObjectOutputStream(new GZIPOutputStream(preParserBytesWriter))) {_.writeObject(worker.preParser)}
    val lexerBytesWriter = new ByteArrayOutputStream()
    Using(new ObjectOutputStream(new GZIPOutputStream(lexerBytesWriter))) {_.writeObject(worker.lexer)}
    val preParserBytes = Base64.getEncoder.encodeToString(preParserBytesWriter.toByteArray)
    val lexerBytes = Base64.getEncoder.encodeToString(lexerBytesWriter.toByteArray)

    val nodeClassName = s"${grammarName}Node"

    val packageDefinition =
      Vector("package ", packageName, LS)
    val imports =
      Vector("import java.io.{ObjectInputStream, ByteArrayInputStream}", LS, "import java.util.zip.GZIPInputStream", LS, "import java.util.Base64", LS)
    def packageObjectDefinition(innerPart: Vector[String]): Vector[String] = {
      innerPart
        .prepended(
          s"package object generated {$LS${INDENT}sealed trait $nodeClassName$LS$LS"
          )
        .appended(s"}$LS")
    }

    val terminals = grammar.terminals.view
                           .collect { case desc@TerminalDescriptor(name) if desc.normal =>
                             s"${INDENT}final case class `$name`(value: ${desc.typeName}) extends $nodeClassName"
                           }
                           .flatMap(Seq(_, LS))
                           .toVector

    val ruleElementToString: PartialFunction[(ElementDescriptor[?], Int), String] = {
      case (desc@TerminalDescriptor(name), i)   if desc.normal => s"`$name$i`: `$name`"
      case (desc@NonTerminalDescriptor(name), i) if desc.normal => s"`$name$i`: `$name`"
    }

    val nonTerminals: Vector[String] =
      grammar.nonTerminals
        .view
        .collect { case desc@NonTerminalDescriptor(name) if desc.normal =>
          val childrenClassName = s"`${name}Children`"
          Vector(
            LS,
            s"${INDENT}final case class `$name`(children: $childrenClassName, value: ${desc.typeName}) extends $nodeClassName",
            LS,
            s"${INDENT}sealed trait $childrenClassName",
            LS,
            ) ++ grammar.nonTerminalRules.collect {
            case NonTerminalRule(lhs, rhs, _) if lhs == desc =>
              s"${INDENT}final case class `$name->${rhs.map(_.name).mkString("")}`(${rhs.zipWithIndex.collect(ruleElementToString).mkString(", ")}) extends $childrenClassName$LS"
          }
        }
        .flatten
        .toVector.appended(LS)

    val parser: Vector[String] = Vector(
      s"${INDENT}private val parser: eliseevh.parsergen.parser.Parser = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(Base64.getDecoder.decode(\"$preParserBytes\")))).readObject().asInstanceOf[eliseevh.parsergen.parser.Parser]$LS"
      )
    val lexer: Vector[String] = Vector(
      s"${INDENT}private val lexer: eliseevh.parsergen.lexer.Lexer = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(Base64.getDecoder.decode(\"$lexerBytes\")))).readObject().asInstanceOf[eliseevh.parsergen.lexer.Lexer]$LS"
      )
    val fromTree: Vector[String] =
      (grammar.terminals.collect { case desc@TerminalDescriptor(name) if desc.normal =>
        s"${INDENT * 2}case eliseevh.parsergen.result.Leaf(token) if token.descriptor.name == \"$name\" => `$name`(token.value.asInstanceOf[${desc.typeName}])$LS"
      }.toVector.appended(LS) ++ grammar.nonTerminalRules.collect {
        case rule@NonTerminalRule(NonTerminalDescriptor(lhsName), rightSide, _) =>
          val collectedRightSide = rightSide.collect {
            case desc@TerminalDescriptor(name) if desc.normal =>
              s"eliseevh.parsergen.generic.TerminalDescriptor(\"$name\")"
            case desc@NonTerminalDescriptor(name) if desc.normal =>
              s"eliseevh.parsergen.generic.NonTerminalDescriptor(\"$name\")"
          }
          s"${INDENT * 2}case eliseevh.parsergen.result.Node(rule, children, value) if rule.descriptor.name == \"$lhsName\" " +
            s"&& rule.rightElements == List(${collectedRightSide.mkString(", ")}) => " +
            s"children match {$LS${INDENT * 3}case List(${collectedRightSide.view.zipWithIndex
             .map(value => s"v${value._2}")
             .mkString(", ")}) => `$lhsName`(`$lhsName->${rightSide.map(_.name).mkString("")}`(${rightSide.view.collect {
                                            case desc@TerminalDescriptor(name) if desc.normal => name
                                            case desc@NonTerminalDescriptor(name) if desc.normal => name}
                                          .zipWithIndex
                                          .map { case (name, idx) =>
                                            s"fromTree(v$idx).asInstanceOf[`$name`]"
                                          }
                                          .mkString(", ")}), value.asInstanceOf[${rule.descriptor.typeName}])$LS${INDENT * 2}}$LS"

      }.toVector)
        .prepended(
          s"${INDENT}private def fromTree(tree: eliseevh.parsergen.result.Tree[?]): $nodeClassName = tree match {$LS"
          )
        .appended(s"$INDENT}$LS")

    val parseMethodDeclaration: Vector[String] = Vector(
      s"${INDENT}def parse$grammarName(input: String): `${grammar.start.name}` = fromTree(parser.parse(lexer.tokenize(input))).asInstanceOf[`${grammar.start.name}`]"
      )
    val generatedFileContent: Vector[String] =
      packageDefinition.appended(LS) ++ imports.appended(LS) ++ packageObjectDefinition(
        terminals.appended(LS) ++ nonTerminals.appended(LS) ++ fromTree.appended(LS).appended(LS) ++ parser ++ lexer.appended(LS) ++ parseMethodDeclaration.appended(LS)
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
}
