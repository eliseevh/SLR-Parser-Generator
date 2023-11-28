package eliseevh.parsergen.grammar.parser

import eliseevh.parsergen.syntax.{:=, TypeDesc}
import eliseevh.parsergen.grammar.ElementDescriptor

import scala.reflect.runtime.universe._

case class NonTerminalDescriptor[+R: TypeTag](override val name: String)(
    implicit nothingDefault: R := Unit
) extends ElementDescriptor[R] {
  override val typeName: String = typeTag.tpe.toString
  def withType[Type: TypeTag]: NonTerminalDescriptor[Type] =
    NonTerminalDescriptor[Type](name)

  def withValue[Type](typeDesc: TypeDesc[Type]): NonTerminalDescriptor[Type] = {
    implicit val tag: TypeTag[Type] = typeDesc.tag
    NonTerminalDescriptor[Type](name)
  }
  val normal: Boolean = true
}

object NonTerminalDescriptor {
  final object ExtraStarting extends NonTerminalDescriptor[Nothing]("__InternalExtraStartingNode") {
    override val normal: Boolean = false
  }
}
