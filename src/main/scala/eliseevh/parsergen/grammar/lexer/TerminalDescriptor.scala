package eliseevh.parsergen.grammar.lexer

import eliseevh.parsergen.syntax.{:=, TypeDesc}
import eliseevh.parsergen.grammar.ElementDescriptor

import scala.reflect.runtime.universe._

case class TerminalDescriptor[+R: TypeTag](override val name: String)(implicit
    stringDefault: R := String
) extends ElementDescriptor[R] {
  override val typeName: String = typeTag.tpe.toString
  def withType[Type: TypeTag]: TerminalDescriptor[Type] =
    TerminalDescriptor[Type](name)

  def withValue[Type](typeDesc: TypeDesc[Type]): TerminalDescriptor[Type] = {
    implicit val tag: TypeTag[Type] = typeDesc.tag
    TerminalDescriptor[Type](name)
  }

  val normal: Boolean = true
}
object TerminalDescriptor {
  final object Eof extends TerminalDescriptor[Nothing]("$") {
    override val normal: Boolean = false
  }
}
