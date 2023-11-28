package eliseevh.grammar.goodlang

import eliseevh.parsergen.grammar.Grammar
import java.nio.file.Path

object GoodLangToCGrammar {
  var readintCnt = 0
  class IncorrectAssignmentException
      extends RuntimeException(
        "Count of variables and values in assignment must be equal"
      )

  import eliseevh.parsergen.syntax.GrammarSyntax._
  val grammar: Grammar = for {
    and <- terminal"&&"
    or <- terminal"||"
    not <- terminal"!"

    equals <- terminal"=="
    notEquals <- terminal"!="
    lt <- terminal"<"
    leq <- terminal"<="
    gt <- terminal">"
    geq <- terminal">="

    plus <- terminal"+"
    minus <- terminal"-"
    mul <- terminal"*"
    div <- terminal"/"
    mod <- terminal"%"

    tfor <- terminal"tfor"
    tif <- terminal"tif"
    telse <- terminal"telse"
    let <- terminal"let"
    in <- terminal"in"
    treturn <- terminal"treturn"
    fun <- terminal"fun"

    readint <- terminal"readint"
    tprintln <- terminal"tprintln"

    range <- terminal".."
    semicolon <- terminal";"

    eq <- terminal"="
    comma <- terminal","
    lparen <- terminal"("
    rparen <- terminal")"
    lcurly <- terminal"{"
    rcurly <- terminal"}"

    int <- terminal"int"
    bool <- terminal"bool"
    id <- terminal"id"

    _ <- and --> "and"
    _ <- or --> "or"
    _ <- not --> "not"
    _ <- equals --> "=="
    _ <- notEquals --> "!="
    _ <- lt --> "<"
    _ <- leq --> "<="
    _ <- gt --> ">"
    _ <- geq --> ">="
    _ <- plus --> "\\+"
    _ <- minus --> "-"
    _ <- mul --> "\\*"
    _ <- div --> "/"
    _ <- mod --> "%"
    _ <- tfor --> "for"
    _ <- tif --> "if"
    _ <- telse --> "else"
    _ <- let --> "let"
    _ <- in --> "in"
    _ <- treturn --> "return"
    _ <- fun --> "fun"
    _ <- readint --> "readint"
    _ <- tprintln --> "println"
    _ <- range --> "\\.\\."
    _ <- semicolon --> ";"
    _ <- eq --> "="
    _ <- comma --> "\\,"
    _ <- lparen --> "\\("
    _ <- rparen --> "\\)"
    _ <- lcurly --> "\\{"
    _ <- rcurly --> "\\}"
    _ <- int --> "-?[1-9][0-9]*|0"
    _ <- bool --> "true|false"
    _ <- id --> "[a-zA-Z_][a-zA-Z_0-9]*"
    _ <- skip("[ \\t\\n\\r\\f]+")

    program <- nonTerminal"program" withValue ofType[String]
    def_ <- nonTerminal"def" withValue ofType[String]
    block <- nonTerminal"block" withValue ofType[String]
    stmt <- nonTerminal"stmt" withValue ofType[String]
    idSeq <- nonTerminal"idSeq" withValue ofType[List[String]]
    exprSeq <- nonTerminal"exprSeq" withValue ofType[List[String]]
    assign <- nonTerminal"assign" withValue ofType[String]
    return_ <- nonTerminal"return" withValue ofType[String]
    if_ <- nonTerminal"if" withValue ofType[String]
    else_ <- nonTerminal"else" withValue ofType[String]
    for_ <- nonTerminal"for" withValue ofType[String]
    expr <- nonTerminal"expr" withValue ofType[String]
    println <- nonTerminal"println" withValue ofType[String]

    defs <- nonTerminal"defs" withValue ofType[String]
    _ <- defs --> def_ ~: defs ~: end[String] withConversion { d => rest =>
      d ++ System.lineSeparator() ++ rest
    }
    _ <- defs --> end[String] withConversion System.lineSeparator()
    stmts <- nonTerminal"stmts" withValue ofType[String]
    _ <- stmts --> stmt ~: stmts ~: end[String] withConversion { s => rest =>
      s ++ System.lineSeparator() ++ rest
    }
    _ <- stmts --> end[String] withConversion System.lineSeparator()

    _ <- program --> defs ~: stmts ~: end[String] withConversion { d => s =>
      val LS = System.lineSeparator()
      val result = s"#include <stdio.h>$LS#include <stdlib.h>$LS#include <stdint.h>${LS}int64_t __generated_private_readint_buf_array[$readintCnt]${LS}int64_t* __generated_private_readint_buf() { return __generated_private_readint_buf_array; }$LS${d}int main() {$LS$s}"
      readintCnt = 0
      result
    }

    idseq <- nonTerminal"idseq" withValue ofType[List[String]]

    _ <- idseq --> id ~: comma ~: idseq ~: end[List[String]] withConversion {
      i => _ => rest => i :: rest
    }
    _ <- idSeq --> lparen ~: idseq ~: rparen ~: end[
      List[String]
    ] withConversion { _ => values => _ =>
      values
    }
    _ <- idseq --> id withConversion {
      List(_)
    }
    _ <- idseq --> end[List[String]] withConversion Nil

    _ <- def_ --> fun ~: id ~: idSeq ~: block ~: end[String] withConversion {
      _ => name => params => b =>
        name ++ params.mkString("(", ", ", ")") ++ b
    }
    _ <- block --> lcurly ~: stmts ~: rcurly ~: end[String] withConversion {
      lp => s => rp => lp ++ s ++ rp
    }

    _ <- stmt --> expr ~: semicolon ~: end[String] withConversion { v => _ => v ++ ";" }
    _ <- stmt --> assign ~: semicolon ~: end[String] withConversion { v => _ => v ++ ";" }
    _ <- stmt --> block withConversion identity
    _ <- stmt --> return_ ~: semicolon ~: end[String] withConversion { v => _ => v ++ ";" }
    _ <- stmt --> if_ ~: else_ ~: end[String] withConversion { i => e =>
      i ++ e
    }
    _ <- stmt --> if_ withConversion identity
    _ <- stmt --> for_ withConversion identity
    _ <- stmt --> println ~: semicolon ~: end[String] withConversion { v => _ => v ++ ";" }
    _ <- stmt --> stmt ~: semicolon ~: end[String] withConversion { s => sc =>
      s ++ sc
    }

    exprseq <- nonTerminal"exprseq" withValue ofType[List[String]]
    _ <- exprseq --> expr ~: comma ~: exprseq ~: end[
      List[String]
    ] withConversion { e => _ => es => e :: es }
    _ <- exprseq --> expr withConversion { List(_) }
    _ <- exprseq --> end[List[String]] withConversion Nil

    _ <- exprSeq --> lparen ~: exprseq ~: rparen ~: end[
      List[String]
    ] withConversion { _ => exprs => _ => exprs }

    _ <- assign --> let ~: id ~: eq ~: expr ~: end[String] withConversion {
      _ => name => e => value => "int64_t " ++ name ++ e ++ value
    }
    _ <- assign --> let ~: idSeq ~: eq ~: exprSeq ~: end[
      String
    ] withConversion { _ => names => e => values =>
      if (names.size != values.size) {
        throw new IncorrectAssignmentException
      } else {
        names
          .zip(values)
          .map { case (name, value) => "int64_t " ++ name ++ e ++ value }
          .mkString(";")
      }
    }
    _ <- return_ --> treturn ~: expr ~: end[String] withConversion {
      ret => value => ret ++ " " ++ value
    }

    _ <- if_ --> tif ~: lparen ~: expr ~: rparen ~: block ~: end[String] withConversion {
      i => _ => cond => _ => code =>
        i ++ "(" ++ cond ++ ")" ++ code
    }

    _ <- else_ --> telse ~: stmt ~: end[String] withConversion { e => code =>
      e ++ code
    }

    _ <- for_ --> tfor ~: lparen ~: id ~: in ~: expr ~: range ~: expr ~: rparen ~: stmt ~: end[
      String
    ] withConversion { _ => _ => varname => _ => lb => _ => rb => _ => code =>
      s"for (int64_t $varname = $lb;$varname < $rb; $varname++) $code"
    }

    _ <- expr --> id ~: eq ~: expr ~: end[String] withConversion {
      name => e => value => name ++ e ++ value
    }

    binaryOp <- nonTerminal"binaryOp" withValue ofType[String]
    _ <- binaryOp --> plus withConversion identity
    _ <- binaryOp --> minus withConversion identity
    _ <- binaryOp --> mul withConversion identity
    _ <- binaryOp --> div withConversion identity
    _ <- binaryOp --> mod withConversion identity
    _ <- binaryOp --> and withConversion { _ => "&&" }
    _ <- binaryOp --> or withConversion { _ => "||" }
    _ <- binaryOp --> equals withConversion identity
    _ <- binaryOp --> notEquals withConversion identity
    _ <- binaryOp --> lt withConversion identity
    _ <- binaryOp --> gt withConversion identity
    _ <- binaryOp --> leq withConversion identity
    _ <- binaryOp --> geq withConversion identity

    unaryOp <- nonTerminal"unaryOp" withValue ofType[String]
    _ <- unaryOp --> minus withConversion identity
    _ <- unaryOp --> not withConversion { _ => "!" }

    _ <- expr --> expr ~: binaryOp ~: expr ~: end[String] withConversion {
      l => op => r => l ++ op ++ r
    }
    _ <- expr --> unaryOp ~: expr ~: end[String] withConversion { op => v =>
      op ++ v
    }

    _ <- expr --> id ~: exprSeq ~: end[String] withConversion { name => args =>
      s"$name${args.mkString("(", ", ", ")")}"
    }

    _ <- expr --> int withConversion identity
    _ <- expr --> bool withConversion identity
    _ <- expr --> id withConversion identity
    _ <- expr --> readint withConversion { _ =>
      val addr = s"__generated_private_readint_buf() + $readintCnt"
      readintCnt += 1
      s"(scanf(\"%ld\", $addr), *($addr))"
    }

    _ <- expr --> lparen ~: expr ~: rparen ~: end[String] withConversion {
      lp => v => rp => lp ++ v ++ rp
    }

    _ <- println --> tprintln ~: exprSeq ~: end[String] withConversion {
      _ => values =>
        s"printf(${values.map(_ => "%ld").mkString("\"", " ", "\\n\"")}${values
          .mkString(", ", ", ", ")")}"
    }

  } yield program

  def main(args: Array[String]): Unit = {
    grammar withName "GoodLang" inPackage "eliseevh.goodlang" generateIn Path
      .of("src", "main", "scala")
  }
}
