package eliseevh.goodlang

import java.io.{ObjectInputStream, ByteArrayInputStream}
import java.util.zip.GZIPInputStream
import java.util.Base64

package object generated {
    sealed trait GoodLangNode

    final case class `>=`(value: String) extends GoodLangNode
    final case class `readint`(value: String) extends GoodLangNode
    final case class `tif`(value: String) extends GoodLangNode
    final case class `>`(value: String) extends GoodLangNode
    final case class `!`(value: String) extends GoodLangNode
    final case class `fun`(value: String) extends GoodLangNode
    final case class `int`(value: String) extends GoodLangNode
    final case class `||`(value: String) extends GoodLangNode
    final case class `{`(value: String) extends GoodLangNode
    final case class `id`(value: String) extends GoodLangNode
    final case class `&&`(value: String) extends GoodLangNode
    final case class `+`(value: String) extends GoodLangNode
    final case class `telse`(value: String) extends GoodLangNode
    final case class `tprintln`(value: String) extends GoodLangNode
    final case class `(`(value: String) extends GoodLangNode
    final case class `<`(value: String) extends GoodLangNode
    final case class `,`(value: String) extends GoodLangNode
    final case class `in`(value: String) extends GoodLangNode
    final case class `}`(value: String) extends GoodLangNode
    final case class `tfor`(value: String) extends GoodLangNode
    final case class `/`(value: String) extends GoodLangNode
    final case class `..`(value: String) extends GoodLangNode
    final case class `!=`(value: String) extends GoodLangNode
    final case class `)`(value: String) extends GoodLangNode
    final case class `;`(value: String) extends GoodLangNode
    final case class `treturn`(value: String) extends GoodLangNode
    final case class `%`(value: String) extends GoodLangNode
    final case class `let`(value: String) extends GoodLangNode
    final case class `=`(value: String) extends GoodLangNode
    final case class `*`(value: String) extends GoodLangNode
    final case class `bool`(value: String) extends GoodLangNode
    final case class `-`(value: String) extends GoodLangNode
    final case class `<=`(value: String) extends GoodLangNode
    final case class `==`(value: String) extends GoodLangNode


    final case class `def`(children: `defChildren`, value: String) extends GoodLangNode
    sealed trait `defChildren`
    final case class `def->funididSeqblock`(`fun0`: `fun`, `id1`: `id`, `idSeq2`: `idSeq`, `block3`: `block`) extends `defChildren`

    final case class `idSeq`(children: `idSeqChildren`, value: List[String]) extends GoodLangNode
    sealed trait `idSeqChildren`
    final case class `idSeq->(idseq)`(`(0`: `(`, `idseq1`: `idseq`, `)2`: `)`) extends `idSeqChildren`

    final case class `unaryOp`(children: `unaryOpChildren`, value: String) extends GoodLangNode
    sealed trait `unaryOpChildren`
    final case class `unaryOp->!`(`!0`: `!`) extends `unaryOpChildren`
    final case class `unaryOp->-`(`-0`: `-`) extends `unaryOpChildren`

    final case class `exprseq`(children: `exprseqChildren`, value: List[String]) extends GoodLangNode
    sealed trait `exprseqChildren`
    final case class `exprseq->`() extends `exprseqChildren`
    final case class `exprseq->expr,exprseq`(`expr0`: `expr`, `,1`: `,`, `exprseq2`: `exprseq`) extends `exprseqChildren`
    final case class `exprseq->expr`(`expr0`: `expr`) extends `exprseqChildren`

    final case class `stmt`(children: `stmtChildren`, value: String) extends GoodLangNode
    sealed trait `stmtChildren`
    final case class `stmt->stmt;`(`stmt0`: `stmt`, `;1`: `;`) extends `stmtChildren`
    final case class `stmt->assign;`(`assign0`: `assign`, `;1`: `;`) extends `stmtChildren`
    final case class `stmt->return;`(`return0`: `return`, `;1`: `;`) extends `stmtChildren`
    final case class `stmt->block`(`block0`: `block`) extends `stmtChildren`
    final case class `stmt->expr;`(`expr0`: `expr`, `;1`: `;`) extends `stmtChildren`
    final case class `stmt->for`(`for0`: `for`) extends `stmtChildren`
    final case class `stmt->if`(`if0`: `if`) extends `stmtChildren`
    final case class `stmt->ifelse`(`if0`: `if`, `else1`: `else`) extends `stmtChildren`
    final case class `stmt->println;`(`println0`: `println`, `;1`: `;`) extends `stmtChildren`

    final case class `println`(children: `printlnChildren`, value: String) extends GoodLangNode
    sealed trait `printlnChildren`
    final case class `println->tprintlnexprSeq`(`tprintln0`: `tprintln`, `exprSeq1`: `exprSeq`) extends `printlnChildren`

    final case class `idseq`(children: `idseqChildren`, value: List[String]) extends GoodLangNode
    sealed trait `idseqChildren`
    final case class `idseq->id`(`id0`: `id`) extends `idseqChildren`
    final case class `idseq->`() extends `idseqChildren`
    final case class `idseq->id,idseq`(`id0`: `id`, `,1`: `,`, `idseq2`: `idseq`) extends `idseqChildren`

    final case class `block`(children: `blockChildren`, value: String) extends GoodLangNode
    sealed trait `blockChildren`
    final case class `block->{stmts}`(`{0`: `{`, `stmts1`: `stmts`, `}2`: `}`) extends `blockChildren`

    final case class `return`(children: `returnChildren`, value: String) extends GoodLangNode
    sealed trait `returnChildren`
    final case class `return->treturnexpr`(`treturn0`: `treturn`, `expr1`: `expr`) extends `returnChildren`

    final case class `assign`(children: `assignChildren`, value: String) extends GoodLangNode
    sealed trait `assignChildren`
    final case class `assign->letidSeq=exprSeq`(`let0`: `let`, `idSeq1`: `idSeq`, `=2`: `=`, `exprSeq3`: `exprSeq`) extends `assignChildren`
    final case class `assign->letid=expr`(`let0`: `let`, `id1`: `id`, `=2`: `=`, `expr3`: `expr`) extends `assignChildren`

    final case class `else`(children: `elseChildren`, value: String) extends GoodLangNode
    sealed trait `elseChildren`
    final case class `else->telsestmt`(`telse0`: `telse`, `stmt1`: `stmt`) extends `elseChildren`

    final case class `if`(children: `ifChildren`, value: String) extends GoodLangNode
    sealed trait `ifChildren`
    final case class `if->tif(expr)block`(`tif0`: `tif`, `(1`: `(`, `expr2`: `expr`, `)3`: `)`, `block4`: `block`) extends `ifChildren`

    final case class `program`(children: `programChildren`, value: String) extends GoodLangNode
    sealed trait `programChildren`
    final case class `program->defsstmts`(`defs0`: `defs`, `stmts1`: `stmts`) extends `programChildren`

    final case class `exprSeq`(children: `exprSeqChildren`, value: List[String]) extends GoodLangNode
    sealed trait `exprSeqChildren`
    final case class `exprSeq->(exprseq)`(`(0`: `(`, `exprseq1`: `exprseq`, `)2`: `)`) extends `exprSeqChildren`

    final case class `for`(children: `forChildren`, value: String) extends GoodLangNode
    sealed trait `forChildren`
    final case class `for->tfor(idinexpr..expr)stmt`(`tfor0`: `tfor`, `(1`: `(`, `id2`: `id`, `in3`: `in`, `expr4`: `expr`, `..5`: `..`, `expr6`: `expr`, `)7`: `)`, `stmt8`: `stmt`) extends `forChildren`

    final case class `expr`(children: `exprChildren`, value: String) extends GoodLangNode
    sealed trait `exprChildren`
    final case class `expr->exprbinaryOpexpr`(`expr0`: `expr`, `binaryOp1`: `binaryOp`, `expr2`: `expr`) extends `exprChildren`
    final case class `expr->int`(`int0`: `int`) extends `exprChildren`
    final case class `expr->bool`(`bool0`: `bool`) extends `exprChildren`
    final case class `expr->(expr)`(`(0`: `(`, `expr1`: `expr`, `)2`: `)`) extends `exprChildren`
    final case class `expr->unaryOpexpr`(`unaryOp0`: `unaryOp`, `expr1`: `expr`) extends `exprChildren`
    final case class `expr->id`(`id0`: `id`) extends `exprChildren`
    final case class `expr->id=expr`(`id0`: `id`, `=1`: `=`, `expr2`: `expr`) extends `exprChildren`
    final case class `expr->idexprSeq`(`id0`: `id`, `exprSeq1`: `exprSeq`) extends `exprChildren`
    final case class `expr->readint`(`readint0`: `readint`) extends `exprChildren`

    final case class `defs`(children: `defsChildren`, value: String) extends GoodLangNode
    sealed trait `defsChildren`
    final case class `defs->`() extends `defsChildren`
    final case class `defs->defdefs`(`def0`: `def`, `defs1`: `defs`) extends `defsChildren`

    final case class `binaryOp`(children: `binaryOpChildren`, value: String) extends GoodLangNode
    sealed trait `binaryOpChildren`
    final case class `binaryOp->==`(`==0`: `==`) extends `binaryOpChildren`
    final case class `binaryOp->&&`(`&&0`: `&&`) extends `binaryOpChildren`
    final case class `binaryOp-><=`(`<=0`: `<=`) extends `binaryOpChildren`
    final case class `binaryOp->/`(`/0`: `/`) extends `binaryOpChildren`
    final case class `binaryOp->-`(`-0`: `-`) extends `binaryOpChildren`
    final case class `binaryOp-><`(`<0`: `<`) extends `binaryOpChildren`
    final case class `binaryOp->%`(`%0`: `%`) extends `binaryOpChildren`
    final case class `binaryOp->!=`(`!=0`: `!=`) extends `binaryOpChildren`
    final case class `binaryOp->*`(`*0`: `*`) extends `binaryOpChildren`
    final case class `binaryOp->+`(`+0`: `+`) extends `binaryOpChildren`
    final case class `binaryOp->>=`(`>=0`: `>=`) extends `binaryOpChildren`
    final case class `binaryOp->||`(`||0`: `||`) extends `binaryOpChildren`
    final case class `binaryOp->>`(`>0`: `>`) extends `binaryOpChildren`

    final case class `stmts`(children: `stmtsChildren`, value: String) extends GoodLangNode
    sealed trait `stmtsChildren`
    final case class `stmts->`() extends `stmtsChildren`
    final case class `stmts->stmtstmts`(`stmt0`: `stmt`, `stmts1`: `stmts`) extends `stmtsChildren`


    private def fromTree(tree: eliseevh.parsergen.runtime.Tree[?]): GoodLangNode = tree match {
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "tprintln" => `tprintln`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "bool" => `bool`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == ".." => `..`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "," => `,`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == ")" => `)`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "in" => `in`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "<" => `<`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == ">" => `>`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "==" => `==`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "treturn" => `treturn`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "-" => `-`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == ">=" => `>=`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "!=" => `!=`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "!" => `!`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "fun" => `fun`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "||" => `||`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "=" => `=`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "*" => `*`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "int" => `int`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "readint" => `readint`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "%" => `%`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "+" => `+`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "}" => `}`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "(" => `(`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "<=" => `<=`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "telse" => `telse`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "id" => `id`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == ";" => `;`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "let" => `let`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "/" => `/`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "tif" => `tif`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "{" => `{`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "&&" => `&&`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "tfor" => `tfor`(token.value.asInstanceOf[String])

        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "defs" && rule.rightElements == List() => children match {
            case List() => `defs`(`defs->`(), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "exprseq" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("expr")) => children match {
            case List(v0) => `exprseq`(`exprseq->expr`(fromTree(v0).asInstanceOf[`expr`]), value.asInstanceOf[List[String]])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "idseq" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("id")) => children match {
            case List(v0) => `idseq`(`idseq->id`(fromTree(v0).asInstanceOf[`id`]), value.asInstanceOf[List[String]])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "binaryOp" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("&&")) => children match {
            case List(v0) => `binaryOp`(`binaryOp->&&`(fromTree(v0).asInstanceOf[`&&`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "stmt" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("block")) => children match {
            case List(v0) => `stmt`(`stmt->block`(fromTree(v0).asInstanceOf[`block`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "binaryOp" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("*")) => children match {
            case List(v0) => `binaryOp`(`binaryOp->*`(fromTree(v0).asInstanceOf[`*`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "stmt" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("return"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(";")) => children match {
            case List(v0, v1) => `stmt`(`stmt->return;`(fromTree(v0).asInstanceOf[`return`], fromTree(v1).asInstanceOf[`;`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "binaryOp" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("||")) => children match {
            case List(v0) => `binaryOp`(`binaryOp->||`(fromTree(v0).asInstanceOf[`||`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "expr" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("readint")) => children match {
            case List(v0) => `expr`(`expr->readint`(fromTree(v0).asInstanceOf[`readint`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "unaryOp" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("!")) => children match {
            case List(v0) => `unaryOp`(`unaryOp->!`(fromTree(v0).asInstanceOf[`!`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "expr" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("expr"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("binaryOp"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("expr")) => children match {
            case List(v0, v1, v2) => `expr`(`expr->exprbinaryOpexpr`(fromTree(v0).asInstanceOf[`expr`], fromTree(v1).asInstanceOf[`binaryOp`], fromTree(v2).asInstanceOf[`expr`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "binaryOp" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("-")) => children match {
            case List(v0) => `binaryOp`(`binaryOp->-`(fromTree(v0).asInstanceOf[`-`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "binaryOp" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("/")) => children match {
            case List(v0) => `binaryOp`(`binaryOp->/`(fromTree(v0).asInstanceOf[`/`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "println" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("tprintln"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("exprSeq")) => children match {
            case List(v0, v1) => `println`(`println->tprintlnexprSeq`(fromTree(v0).asInstanceOf[`tprintln`], fromTree(v1).asInstanceOf[`exprSeq`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "binaryOp" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor(">")) => children match {
            case List(v0) => `binaryOp`(`binaryOp->>`(fromTree(v0).asInstanceOf[`>`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "stmt" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("for")) => children match {
            case List(v0) => `stmt`(`stmt->for`(fromTree(v0).asInstanceOf[`for`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "block" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("{"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("stmts"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("}")) => children match {
            case List(v0, v1, v2) => `block`(`block->{stmts}`(fromTree(v0).asInstanceOf[`{`], fromTree(v1).asInstanceOf[`stmts`], fromTree(v2).asInstanceOf[`}`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "binaryOp" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("<")) => children match {
            case List(v0) => `binaryOp`(`binaryOp-><`(fromTree(v0).asInstanceOf[`<`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "expr" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("id")) => children match {
            case List(v0) => `expr`(`expr->id`(fromTree(v0).asInstanceOf[`id`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "binaryOp" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor(">=")) => children match {
            case List(v0) => `binaryOp`(`binaryOp->>=`(fromTree(v0).asInstanceOf[`>=`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "defs" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("def"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("defs")) => children match {
            case List(v0, v1) => `defs`(`defs->defdefs`(fromTree(v0).asInstanceOf[`def`], fromTree(v1).asInstanceOf[`defs`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "stmt" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("assign"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(";")) => children match {
            case List(v0, v1) => `stmt`(`stmt->assign;`(fromTree(v0).asInstanceOf[`assign`], fromTree(v1).asInstanceOf[`;`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "return" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("treturn"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("expr")) => children match {
            case List(v0, v1) => `return`(`return->treturnexpr`(fromTree(v0).asInstanceOf[`treturn`], fromTree(v1).asInstanceOf[`expr`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "binaryOp" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("!=")) => children match {
            case List(v0) => `binaryOp`(`binaryOp->!=`(fromTree(v0).asInstanceOf[`!=`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "expr" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("unaryOp"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("expr")) => children match {
            case List(v0, v1) => `expr`(`expr->unaryOpexpr`(fromTree(v0).asInstanceOf[`unaryOp`], fromTree(v1).asInstanceOf[`expr`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "if" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("tif"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("("), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("expr"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(")"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("block")) => children match {
            case List(v0, v1, v2, v3, v4) => `if`(`if->tif(expr)block`(fromTree(v0).asInstanceOf[`tif`], fromTree(v1).asInstanceOf[`(`], fromTree(v2).asInstanceOf[`expr`], fromTree(v3).asInstanceOf[`)`], fromTree(v4).asInstanceOf[`block`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "expr" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("id"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("exprSeq")) => children match {
            case List(v0, v1) => `expr`(`expr->idexprSeq`(fromTree(v0).asInstanceOf[`id`], fromTree(v1).asInstanceOf[`exprSeq`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "idSeq" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("("), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("idseq"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(")")) => children match {
            case List(v0, v1, v2) => `idSeq`(`idSeq->(idseq)`(fromTree(v0).asInstanceOf[`(`], fromTree(v1).asInstanceOf[`idseq`], fromTree(v2).asInstanceOf[`)`]), value.asInstanceOf[List[String]])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "for" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("tfor"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("("), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("id"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("in"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("expr"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(".."), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("expr"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(")"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("stmt")) => children match {
            case List(v0, v1, v2, v3, v4, v5, v6, v7, v8) => `for`(`for->tfor(idinexpr..expr)stmt`(fromTree(v0).asInstanceOf[`tfor`], fromTree(v1).asInstanceOf[`(`], fromTree(v2).asInstanceOf[`id`], fromTree(v3).asInstanceOf[`in`], fromTree(v4).asInstanceOf[`expr`], fromTree(v5).asInstanceOf[`..`], fromTree(v6).asInstanceOf[`expr`], fromTree(v7).asInstanceOf[`)`], fromTree(v8).asInstanceOf[`stmt`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "exprSeq" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("("), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("exprseq"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(")")) => children match {
            case List(v0, v1, v2) => `exprSeq`(`exprSeq->(exprseq)`(fromTree(v0).asInstanceOf[`(`], fromTree(v1).asInstanceOf[`exprseq`], fromTree(v2).asInstanceOf[`)`]), value.asInstanceOf[List[String]])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "expr" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("bool")) => children match {
            case List(v0) => `expr`(`expr->bool`(fromTree(v0).asInstanceOf[`bool`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "stmts" && rule.rightElements == List() => children match {
            case List() => `stmts`(`stmts->`(), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "binaryOp" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("==")) => children match {
            case List(v0) => `binaryOp`(`binaryOp->==`(fromTree(v0).asInstanceOf[`==`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "binaryOp" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("+")) => children match {
            case List(v0) => `binaryOp`(`binaryOp->+`(fromTree(v0).asInstanceOf[`+`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "stmt" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("expr"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(";")) => children match {
            case List(v0, v1) => `stmt`(`stmt->expr;`(fromTree(v0).asInstanceOf[`expr`], fromTree(v1).asInstanceOf[`;`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "binaryOp" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("%")) => children match {
            case List(v0) => `binaryOp`(`binaryOp->%`(fromTree(v0).asInstanceOf[`%`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "expr" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("id"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("="), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("expr")) => children match {
            case List(v0, v1, v2) => `expr`(`expr->id=expr`(fromTree(v0).asInstanceOf[`id`], fromTree(v1).asInstanceOf[`=`], fromTree(v2).asInstanceOf[`expr`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "def" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("fun"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("id"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("idSeq"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("block")) => children match {
            case List(v0, v1, v2, v3) => `def`(`def->funididSeqblock`(fromTree(v0).asInstanceOf[`fun`], fromTree(v1).asInstanceOf[`id`], fromTree(v2).asInstanceOf[`idSeq`], fromTree(v3).asInstanceOf[`block`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "program" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("defs"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("stmts")) => children match {
            case List(v0, v1) => `program`(`program->defsstmts`(fromTree(v0).asInstanceOf[`defs`], fromTree(v1).asInstanceOf[`stmts`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "exprseq" && rule.rightElements == List() => children match {
            case List() => `exprseq`(`exprseq->`(), value.asInstanceOf[List[String]])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "exprseq" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("expr"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(","), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("exprseq")) => children match {
            case List(v0, v1, v2) => `exprseq`(`exprseq->expr,exprseq`(fromTree(v0).asInstanceOf[`expr`], fromTree(v1).asInstanceOf[`,`], fromTree(v2).asInstanceOf[`exprseq`]), value.asInstanceOf[List[String]])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "stmt" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("if"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("else")) => children match {
            case List(v0, v1) => `stmt`(`stmt->ifelse`(fromTree(v0).asInstanceOf[`if`], fromTree(v1).asInstanceOf[`else`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "stmt" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("if")) => children match {
            case List(v0) => `stmt`(`stmt->if`(fromTree(v0).asInstanceOf[`if`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "unaryOp" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("-")) => children match {
            case List(v0) => `unaryOp`(`unaryOp->-`(fromTree(v0).asInstanceOf[`-`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "binaryOp" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("<=")) => children match {
            case List(v0) => `binaryOp`(`binaryOp-><=`(fromTree(v0).asInstanceOf[`<=`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "stmt" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("println"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(";")) => children match {
            case List(v0, v1) => `stmt`(`stmt->println;`(fromTree(v0).asInstanceOf[`println`], fromTree(v1).asInstanceOf[`;`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "expr" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("("), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("expr"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(")")) => children match {
            case List(v0, v1, v2) => `expr`(`expr->(expr)`(fromTree(v0).asInstanceOf[`(`], fromTree(v1).asInstanceOf[`expr`], fromTree(v2).asInstanceOf[`)`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "stmt" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("stmt"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(";")) => children match {
            case List(v0, v1) => `stmt`(`stmt->stmt;`(fromTree(v0).asInstanceOf[`stmt`], fromTree(v1).asInstanceOf[`;`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "expr" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("int")) => children match {
            case List(v0) => `expr`(`expr->int`(fromTree(v0).asInstanceOf[`int`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "stmts" && rule.rightElements == List(eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("stmt"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("stmts")) => children match {
            case List(v0, v1) => `stmts`(`stmts->stmtstmts`(fromTree(v0).asInstanceOf[`stmt`], fromTree(v1).asInstanceOf[`stmts`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "assign" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("let"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("id"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("="), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("expr")) => children match {
            case List(v0, v1, v2, v3) => `assign`(`assign->letid=expr`(fromTree(v0).asInstanceOf[`let`], fromTree(v1).asInstanceOf[`id`], fromTree(v2).asInstanceOf[`=`], fromTree(v3).asInstanceOf[`expr`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "idseq" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("id"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(","), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("idseq")) => children match {
            case List(v0, v1, v2) => `idseq`(`idseq->id,idseq`(fromTree(v0).asInstanceOf[`id`], fromTree(v1).asInstanceOf[`,`], fromTree(v2).asInstanceOf[`idseq`]), value.asInstanceOf[List[String]])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "else" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("telse"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("stmt")) => children match {
            case List(v0, v1) => `else`(`else->telsestmt`(fromTree(v0).asInstanceOf[`telse`], fromTree(v1).asInstanceOf[`stmt`]), value.asInstanceOf[String])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "idseq" && rule.rightElements == List() => children match {
            case List() => `idseq`(`idseq->`(), value.asInstanceOf[List[String]])
        }
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "assign" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("let"), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("idSeq"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("="), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("exprSeq")) => children match {
            case List(v0, v1, v2, v3) => `assign`(`assign->letidSeq=exprSeq`(fromTree(v0).asInstanceOf[`let`], fromTree(v1).asInstanceOf[`idSeq`], fromTree(v2).asInstanceOf[`=`], fromTree(v3).asInstanceOf[`exprSeq`]), value.asInstanceOf[String])
        }
    }


    private val parser: eliseevh.parsergen.runtime.Parser = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(Base64.getDecoder.decode("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")))).readObject().asInstanceOf[eliseevh.parsergen.runtime.Parser]
    private val lexer: eliseevh.parsergen.runtime.Lexer = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(Base64.getDecoder.decode("H4sIAAAAAAAA/41Xz28bRRQer/OrSUkpFYGCkOLKieI03k1AIKH8QFVDIGKTVnWEUO0IJt6xM8ns7GZ21nEalxviyoEr9MiFCyduwF+AuPQf6L1SJU5wgZnZXduJPQM+rOyZ973vzfe+t+v98TkYjRiYRQRHCLUO7RCyCLEmojaLKcc+sl3URuzjP38IOp8+e2KBnAtGWUxQxEHBjeqQQKceEILqHAfUwb4fc3hAkOPiiK+2Q5H8bRVl96JskR4xXLc3UQPGhFfED0jwIyg377OgfQaSTz4v6cYbsM4DdsbBG4OEW8lewrQ4wLTNEZPlpGHFvSD9ljFYFxluDTJcSpEwzSdMmUY7gSck0Z4jp1imfBV1l8BIiHfdPYIt6BBIm45aEolbohMDR+hKaktJi73CQTs8HflHfEQ97wxpYJNB34fMJrKB9m7AfEj2EPMxheSBKGTi8OSX3/7+a8ICeRdM1gPaQiwSjLK2RIWtmKoaVlZFgIeiOsOh0ICDd92Mz+nyOSmfo/icjGmzC1uVzkFN1ObgzZQg5pg4PuT1QyxkeCA3E30LUhxbimNj2gqOkZ2JizwX+gceDOAf3y6V3vvKApPbYBr7IdlB/DDwPsHUq4KrdRjymCHvDmsKsW9U+9S+d3AkpBXVTCdBglo14AR8CSZd8HojPTUk21T0Xngj69krfVkqXAJFlreGxCel7EIfyaSvuuCWNqiCmxTKUtPIK/Io3XrEQt/Z+hLe6K1ezjCDacShMCbkyEtC9s7CZLcdCu+MxSxTJJE4VeSb3z978nJUIspa0mTCjwtDjBWdUQ7b9kdJvyvqV+ZL+WmHHFy75CAORmEYEjFi8wuDvSgNLvEedZ/HEmrnIrXwUxHSgAqNi6eYH+4Gd7tmLq5cYkz7VhpckgLN/q9ZGvS2P7WZ3/m5cypm6SEYo2rWXDBCew2b4KIH3Qa2wxwH1vw8B2MJu+C9mYy+nAk7mwlbzcSz50+Pvn9R+NUClrhZhZALB4kxvZmcQQ2RGiznfrIlXDmnkhXlXjFLVlTJisUmC+JQliIsPWu6h1fQSTKOrymvqMoUkZ0SbTWfHtNt6yVR2DYYbRDYjHoF9gwHOMhD6ilH59V1TF6mTkeUXdTKtLzMqK8FqU2n09NGLs6paynJZgVM/loahswVDMA8DbgWaa2vmzjX1/XIghFZ0CNzawZgbk3PuGZkXDMwbpgYN/SMG0bGDQPjbROwdlsPLJtKLetxi0bCRT3QMRE6etycCTenxY3whnyc6u3aMBg9z3HDdE7c0EJHOSIRMoBH5L6emSBuqlps622EqbFoqkWOc4bEY84EH0si9IWLR4RR7thAzxD0RPEGfBaizTHBQ4HkxFTEeBqiV9C2TZ2r2TVbb9RVk1FX9TjT7OcMo79knMQlPXDBCFzQA0tGYEkPPDcCz/XAx0bgY70bzW66Vv6gulJ+f7+6LC6LnWX9XeQgCIgh0SRnMeo0oGmkLewZMsxUYfnRnfLDz/ezL6qmJBsDK//5j6lyjMP+d4/zn75b/uLre176Pin/U8hk18W/jQHyq9XZGq/RGqs19vueFVda3dewIa+W3deFD6l38Z2p/S9/E01T9A4AAA==")))).readObject().asInstanceOf[eliseevh.parsergen.runtime.Lexer]

    def parseGoodLang(input: String): `program` = fromTree(parser.parse(lexer.tokenize(input))).asInstanceOf[`program`]
}
