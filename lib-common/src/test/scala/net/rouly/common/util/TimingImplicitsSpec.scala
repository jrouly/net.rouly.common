package net.rouly.common.util

import net.rouly.common.util.TimingImplicits.{TimeableFuture, TimeableOp}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSpec, TryValues}

import scala.concurrent.Future

class TimingImplicitsSpec
  extends FunSpec
  with TimingFixtures
  with TryValues
  with ScalaFutures {

  describe("TimingImplicits") {

    describe("TimeableOp") {

      describe("timedApply") {
        it("should accurately represent duration") {
          val op = { s: String =>
            Thread.sleep(TestDuration)
            s
          }
          val (s, d) = new TimeableOp(op).timedApply("hello world")
          testDuration(d)
          s shouldEqual "hello world"
        }
      }

      describe("timedApplySafe") {
        it("should accurately represent duration") {
          val op = { s: String =>
            Thread.sleep(TestDuration)
            s
          }
          val (s, d) = new TimeableOp(op).timedApplySafe("hello world")
          testDuration(d)
          s.success.value shouldEqual "hello world"
        }

        it("should accurately represent duration despite errors") {
          val op = { s: String =>
            Thread.sleep(TestDuration)
            throw new RuntimeException("error")
            s
          }
          val (s, d) = new TimeableOp(op).timedApplySafe("hello world")
          testDuration(d)
          s.failure.exception.getMessage shouldEqual "error"
        }
      }

    }

    describe("TimeableFuture") {

      describe("withDuration") {
        it("should accurately represent duration") {
          val f = Future {
            Thread.sleep(TestDuration)
            "hello world"
          }
          val s = new TimeableFuture(f).withDuration { (s, d) =>
            s shouldEqual "hello world"
            testDuration(d)
          }.futureValue
          s shouldEqual "hello world"
        }
      }

      describe("withDurationSafe") {
        it("should accurately represent duration") {
          val f = Future {
            Thread.sleep(TestDuration)
            "hello world"
          }
          val s = new TimeableFuture(f).withDurationSafe { (s, d) =>
            s.success.value shouldEqual "hello world"
            testDuration(d)
          }.futureValue
          s shouldEqual "hello world"
        }

        it("should accurately represent duration despite errors") {
          val f = Future {
            Thread.sleep(TestDuration)
            throw new RuntimeException("error")
            "hello world"
          }
          val s = new TimeableFuture(f).withDurationSafe { (s, d) =>
            s.failure.exception.getMessage shouldEqual "error"
            testDuration(d)
          }.failed.futureValue
          s.getMessage shouldEqual "error"
        }
      }

    }
  }

}
