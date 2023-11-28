package eliseevh.parsergen

import scala.language.implicitConversions
import scala.reflect.runtime.universe._

package object syntax {

  class :=[T, Q]

  trait Default_:= {
    implicit def useProvided[Provided, Default]: Provided := Default =
      new :=[Provided, Default]
  }

  object := extends Default_:= {
    implicit def useDefault[Default]: Default := Default =
      new :=[Default, Default]
  }

  case class TypeDesc[T: TypeTag]() {
    val tag: TypeTag[T] = implicitly
  }
}
