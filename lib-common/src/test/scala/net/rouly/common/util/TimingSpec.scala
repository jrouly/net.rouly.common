package net.rouly.common.util

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSpec, Matchers}

import scala.concurrent.{ExecutionContext, Future}

class TimingSpec
  extends FunSpec
  with ScalaFutures
  with Matchers {

  implicit val ec = ExecutionContext.Implicits.global

  describe("Timing") {

    describe("op") {

      describe("timed") {
        it("should accurately represent duration") {
          val op = { s: String =>
            Thread.sleep(5)
            s
          }
          val s = Timing.timed(op)("hello world")(duration => duration.toMillis.toInt should be >= 1)
          s shouldEqual "hello world"
        }
      }

      describe("withDuration") {
        it("should accurately represent duration") {
          val op = { s: String =>
            Thread.sleep(5)
            "hello world"
          }
          val (s, duration) = Timing.withDuration(op)("hello world")
          s shouldEqual "hello world"
          duration.toMillis.toInt should be >= 1
        }
      }

    }

    describe("future") {
      describe("timed") {
        it("should accurately represent duration") {
          val f = Future {
            Thread.sleep(5)
            "hello world"
          }
          val payload = Timing.timed(f)(duration => duration.toMillis.toInt should be >= 1).futureValue
          payload shouldEqual "hello world"
        }
      }
    }
  }

}
