package net.rouly.common.server.play.util

import java.util.NoSuchElementException

import org.scalatest.{FunSpec, Matchers, OptionValues}

class QueryBindablesSpec extends FunSpec with OptionValues with Matchers {
  import QueryBindablesSpec.Thing
  import QueryBindablesSpec.Thing._

  describe("QueryBindables") {

    describe("pathBinder") {
      val pathBinder = QueryBindables.pathBinder[Thing]("Thing", Thing.byName, _.name)

      it("should bind paths successfully") {
        pathBinder.bind("thing", "one") shouldEqual Right(ThingOne)
        pathBinder.bind("thing", "two") shouldEqual Right(ThingTwo)
      }

      it("should not bind if not bindable") {
        pathBinder.bind("thing", "foo") shouldEqual Left("Cannot parse parameter thing as Thing: invalid thing")
      }

      it("should unbind paths successfully") {
        pathBinder.unbind("thing", ThingOne) shouldEqual "one"
        pathBinder.unbind("thing", ThingTwo) shouldEqual "two"
      }
    }

    describe("queryStringBinder") {
      val queryStringBinder = QueryBindables.queryStringBinder[Thing]("Thing", Thing.byName, _.name)

      it("should bind query strings successfully") {
        queryStringBinder.bind("thing", Map("thing" -> Seq("one"))).value shouldEqual Right(ThingOne)
      }

      it("should not bind query strings if not bindable") {
        queryStringBinder.bind("thing", Map("thing" -> Seq.empty)) shouldBe empty
        queryStringBinder.bind("thing", Map("foo" -> Seq("bar"))) shouldBe empty
        queryStringBinder.bind("thing", Map("thing" -> Seq("bar"))).value shouldBe Left("Cannot parse parameter thing as Thing: invalid thing")
      }

      it("should unbind query strings successfully") {
        queryStringBinder.unbind("thing", ThingOne) shouldEqual "thing=one"
        queryStringBinder.unbind("thing", ThingTwo) shouldEqual "thing=two"
      }
    }

  }

}

object QueryBindablesSpec {
  private sealed abstract class Thing(val name: String)
  private object Thing {

    def all: List[Thing] = List(ThingOne, ThingTwo)
    def byName(name: String): Thing = all.find(_.name == name).getOrElse(throw new NoSuchElementException("invalid thing"))

    case object ThingOne extends Thing("one")
    case object ThingTwo extends Thing("two")
  }
}
