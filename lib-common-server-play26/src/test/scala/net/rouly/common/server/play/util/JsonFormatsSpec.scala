package net.rouly.common.server.play.util

import org.scalatest.{FunSpec, Matchers, OptionValues}
import play.api.libs.json.Json

class JsonFormatsSpec extends FunSpec with Matchers with OptionValues {
  import JsonFormatsSpec.Formattable

  describe("PlayJsonImplicitsSpec") {

    describe("formatAs") {

      it("should format a string serializable thing as a string") {
        implicit val format = JsonFormats.formatAs[Formattable, String](s => Formattable(s.toInt), _.data.toString)
        val formattable = Formattable(500)
        Json.parse("\"500\"").asOpt[Formattable].value shouldEqual formattable
        Json.toJson(formattable) shouldEqual Json.parse("\"500\"")
      }

      it("should format a number serializable thing as a number") {
        implicit val format = JsonFormats.formatAs[Formattable, Int](Formattable.apply, _.data)
        val formattable = Formattable(500)
        Json.parse("500").asOpt[Formattable].value shouldEqual formattable
        Json.toJson(formattable) shouldEqual Json.parse("500")
      }

    }

  }
}

object JsonFormatsSpec {

  private case class Formattable(data: Int)

}
