package net.rouly.common.util

import net.rouly.common.util.TernaryOperator._
import org.scalatest.{FunSpec, Matchers}

class TernaryOperatorSpec
  extends FunSpec
  with Matchers {

  describe("ternary operator") {
    it("should return the left branch") {
      (true ? "left" | "right") shouldEqual Left("left")
    }

    it("should return the right branch") {
      (false ? "left" | 15) shouldEqual Right(15)
    }
  }

}
