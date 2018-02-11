package net.rouly.common.util

import net.rouly.common.util.TimingImplicits._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSpec, Matchers}

import scala.concurrent.{ExecutionContext, Future}

class TimingImplicitsSpec
  extends FunSpec
  with ScalaFutures
  with Matchers {

  implicit val ec = ExecutionContext.Implicits.global

  describe("TimingImplicits") {

    describe("TimeableOp") {

      describe("timed") {
        it("should accurately represent duration") {
          val op = { s: String =>
            Thread.sleep(5)
            s
          }
          val s = op.timedApply("hello world")(duration => duration.toMillis.toInt should be >= 2)
          s shouldEqual "hello world"
        }
      }

      describe("withDuration") {
        it("should accurately represent duration") {
          val op = { s: String =>
            Thread.sleep(5)
            "hello world"
          }
          val (s, duration) = op.applyWithDuration("hello world")
          s shouldEqual "hello world"
          duration.toMillis.toInt should be >= 2
        }
      }

    }

    describe("TimeableFuture") {
      describe("timed") {
        it("should accurately represent duration") {
          val f = Future {
            Thread.sleep(5)
            "hello world"
          }
          val payload = f.timed(duration => duration.toMillis.toInt should be >= 2).futureValue
          payload shouldEqual "hello world"
        }
      }
    }
  }

}
