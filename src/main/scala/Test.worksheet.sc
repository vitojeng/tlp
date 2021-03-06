import scala.compiletime.error
import scala.compiletime.ops.any._
import scala.compiletime.ops.int._
import scala.Tuple._
import scala.util.Not

type Foo[X <: Int] = X match
  case 0 => "Zero"
  case 1 => "One"
  case _ => X

val r0: Foo[0] = "Zero"
val r1: Foo[1] = "One"
val r3: Foo[123] = 123

import scala.quoted._
import scala.quoted.staging._
given Toolbox = Toolbox.make(getClass.getClassLoader)
def showType[T : Type](using QuoteContext): String = summon[Type[T]].show
withQuoteContext:
  showType[Foo[2]]

val i: 1 + 2 = 3
val b: 1 > 2 = false
{
  import scala.compiletime.ops.string._
  val s: "a" + "b" = "ab"
}

case class User(name: String, age: Int)
val user1 = User("walter", 18)
val userT = Tuple.fromProductTyped(user1)
val user2 = User.tupled(userT)
assert(user1 == user2)

val poly0: [t] => t => String = [t] => (x: t) => x match
  case x: Int => "int"
  case x: String => "string"
  case _ => "unknown"

type Switch[T] = T match
  case Int => String
  case String => Int

val poly1: [t] => t => Switch[t] = new PolyFunction:
  def apply[T](x: T): Switch[T] = x match
    case x: Int => x.toString
    case x: String => Integer.parseInt(x).toInt

val poly2: [t] => t => Switch[t] = [T] => (x: T) => x match
    case x: Int => x.toString
    case x: String => Integer.parseInt(x).toInt

poly1("123")
poly1(123)

poly(1)
poly("df")
