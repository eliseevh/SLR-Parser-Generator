package eliseevh.goodlang

import java.io.{ObjectInputStream, ByteArrayInputStream}
import java.util.zip.GZIPInputStream
import java.util.Base64

package object generated {
    sealed trait GoodLangNode

    final case class `>=`(value: String) extends GoodLangNode
    final case class `readint`(value: String) extends GoodLangNode
    final case class `tif`(value: String) extends GoodLangNode
    final case class `!`(value: String) extends GoodLangNode
    final case class `fun`(value: String) extends GoodLangNode
    final case class `int`(value: String) extends GoodLangNode
    final case class `||`(value: String) extends GoodLangNode
    final case class `{`(value: String) extends GoodLangNode
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
    final case class `>`(value: String) extends GoodLangNode
    final case class `tint`(value: String) extends GoodLangNode
    final case class `let`(value: String) extends GoodLangNode
    final case class `=`(value: String) extends GoodLangNode
    final case class `*`(value: String) extends GoodLangNode
    final case class `bool`(value: String) extends GoodLangNode
    final case class `-`(value: String) extends GoodLangNode
    final case class `<=`(value: String) extends GoodLangNode
    final case class `==`(value: String) extends GoodLangNode
    final case class `id`(value: String) extends GoodLangNode
    final case class `:`(value: String) extends GoodLangNode


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
    final case class `idseq->id:tint`(`id0`: `id`, `:1`: `:`, `tint2`: `tint`) extends `idseqChildren`
    final case class `idseq->id`(`id0`: `id`) extends `idseqChildren`
    final case class `idseq->id:tint,idseq`(`id0`: `id`, `:1`: `:`, `tint2`: `tint`, `,3`: `,`, `idseq4`: `idseq`) extends `idseqChildren`
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
    final case class `assign->letid:tint=expr`(`let0`: `let`, `id1`: `id`, `:2`: `:`, `tint3`: `tint`, `=4`: `=`, `expr5`: `expr`) extends `assignChildren`
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
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "==" => `==`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "tint" => `tint`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == "treturn" => `treturn`(token.value.asInstanceOf[String])
        case eliseevh.parsergen.runtime.Leaf(token) if token.descriptor.name == ":" => `:`(token.value.asInstanceOf[String])
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
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "idseq" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("id"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(":"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("tint"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(","), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("idseq")) => children match {
            case List(v0, v1, v2, v3, v4) => `idseq`(`idseq->id:tint,idseq`(fromTree(v0).asInstanceOf[`id`], fromTree(v1).asInstanceOf[`:`], fromTree(v2).asInstanceOf[`tint`], fromTree(v3).asInstanceOf[`,`], fromTree(v4).asInstanceOf[`idseq`]), value.asInstanceOf[List[String]])
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
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "assign" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("let"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("id"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(":"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("tint"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("="), eliseevh.parsergen.grammar.parser.NonTerminalDescriptor("expr")) => children match {
            case List(v0, v1, v2, v3, v4, v5) => `assign`(`assign->letid:tint=expr`(fromTree(v0).asInstanceOf[`let`], fromTree(v1).asInstanceOf[`id`], fromTree(v2).asInstanceOf[`:`], fromTree(v3).asInstanceOf[`tint`], fromTree(v4).asInstanceOf[`=`], fromTree(v5).asInstanceOf[`expr`]), value.asInstanceOf[String])
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
        case eliseevh.parsergen.runtime.Node(rule, children, value) if rule.descriptor.name == "idseq" && rule.rightElements == List(eliseevh.parsergen.grammar.lexer.TerminalDescriptor("id"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor(":"), eliseevh.parsergen.grammar.lexer.TerminalDescriptor("tint")) => children match {
            case List(v0, v1, v2) => `idseq`(`idseq->id:tint`(fromTree(v0).asInstanceOf[`id`], fromTree(v1).asInstanceOf[`:`], fromTree(v2).asInstanceOf[`tint`]), value.asInstanceOf[List[String]])
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
    private val lexer: eliseevh.parsergen.runtime.Lexer = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(Base64.getDecoder.decode("H4sIAAAAAAAA/41Xz28bRRQer50fTUpLqQgUhBRXThSn8W4CAonmB6oaAlE3aVVHCNWOYOIdO5PMzm5mZx2ncbkhrhy4Qo9cuHDiBvwFiEv/gd4rIXGCC8zM7tpOnBnwYWXPvO99b773vV3vDy/ASMTANCI4Qqi9b4eQRYi1ELVZTDn2ke2iDmIf//l90P3k+VML5FwwwmKCIg6KbtSABDqNgBDU4DigDvb9mMM9ghwXR3y5E4rkb6soux9li/SI4Ya9jpowJrwqfkCCH0O5+YAFnROQfPJ5STfWhA0esBMO3hgm3Ej2Eqb5IaZNjpgsJw0r7QTpt4zBOstwc5jhXIqEaTZhyjTaCjwhifYcOcUy6auouwRGQrxr7gFsQ4dA2nLUkkjcFp0YOkJPUltKWuoXDjrhceEf8RH1vHNBA1sM+j5kNpENtLcD5kOyg5iPKSQPRSHj+0c///r3X+MWyLtgohHQNmKRYJS1JSpsxFTVsLQsAjwUNRgOhQYcvOtmfE6Pz0n5HMXnZEzrPdiydA5qoQ4Hb6YEMcfE8SFv7GMhw0O5mehblOLYUhwb03ZwiOxMXOS50N/zYAB//2ah/N6XFpjYBFewH5ItxPcD7x6mXg1cbsCQxwx5d1hLiH29NqD2/b0DIa2o5koSJKhVA47AF2DCBa8301NDsklF74U3sp69MpClyiVQZHnrgviklG3oI5n0VRfc1AZVcYtCWWoaeUkepVePWBg420DC6/3V8xmmMI04FMaEHHlJyM5JmOx2QuGd0ZhliiQSp4p8/dunT1+OykRZS5pM+HHuAmNFJ5TDjv1R0u+q+pX5Un46IQdXzzmIgxEYhkSM2OzccC/Kw0u8Tz3gsYTaOUst/FSCNKBC49Ix5vvbwd2emUtL5xjTvpWHl6RA0/9rloa97U+u57d+6h6LWXoERqmaNRcUaL9h41z0oNfATpjjwJqd5WA0YRe8N5LRlzNhZzNhq5l4/uLZwXd/FH+xgCVuViHkwkFiTG8kZ1BDpAbLeZBsCVfOqGQluVfKkpVUslKpxYI4lKUIS0+b7uFVdJSM42vKK6oyRWSnRButZ4d003pJFLYJRpoEtqJ+gX3DAQ7ykHrK0Xl1HZWXyeOCsotauSIvU+prUWrT7fa1kYsz6lpOslkBk78WLkLmigZgngZci7RWV02cq6t6ZNGILOqRuRUDMLeiZ1wxMq4YGNdMjGt6xjUj45qB8ZYJWL+lB1ZMpVb0uHkj4bwe6JgIHT1uxoSb0eIKvCkfp3q7Ng1Gz3PcNJ0TN7XQEY5IhAzggtzXMxPETVWLbb2NMDUWTbXIMc6QeMyZ4KNJhL5w8Ygwyh0b6BmCnijegM9CtDnGeSiQnJiKGEtD9Aratqlzdbtu6426bDLqsh5nmv2cYfQXjJO4oAfOGYFzemDZCCzrgadG4Kke+MQIfKJ3o9lNVysf1JYq7+/WFsVlvruov4vsBQExJJrgLEbdJjSNdIGbi8mbbG1hzwCdqsHK4zuVR5/tZl/UgfR63jZ57XaCY2DpP/+mVQ9xOPjCc/rjt4uff3XfS19i5R8Zmeya+IszxHO5Nl3ndVpn9ebuwAPqUrv37nfB+2zvHeVD6p19Uev8C8f0O6hpDwAA")))).readObject().asInstanceOf[eliseevh.parsergen.runtime.Lexer]

    def parseGoodLang(input: String): `program` = fromTree(parser.parse(lexer.tokenize(input))).asInstanceOf[`program`]
}
