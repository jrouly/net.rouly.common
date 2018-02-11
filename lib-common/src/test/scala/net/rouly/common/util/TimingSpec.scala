package net.rouly.common.util

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSpec, TryValues}

import scala.concurrent.Future

class TimingSpec
  extends FunSpec
  with TimingFixtures
  with TryValues
  with ScalaFutures {

  describe("Timing") {

    describe("op") {

      describe("withDuration") {
        it("should accurately represent duration") {
          val op = { s: String =>
            Thread.sleep(TestDuration)
            s
          }
          val (s, d) = Timing.withDuration(op("hello world"))
          testDuration(d)
          s shouldEqual "hello world"
        }
      }

      describe("withDurationSafe") {
        it("should accurately represent duration") {
          val op = { s: String =>
            Thread.sleep(TestDuration)
            s
          }
          val (s, d) = Timing.withDurationSafe(op("hello world"))
          testDuration(d)
          s.success.value shouldEqual "hello world"
        }

        it("should accurately represent duration despite errors") {
          val op = { s: String =>
            Thread.sleep(TestDuration)
            throw new RuntimeException("error")
            s
          }
          val (s, d) = Timing.withDurationSafe(op("hello world"))
          testDuration(d)
          s.failure.exception.getMessage shouldEqual "error"
        }
      }

    }

    describe("future") {

      describe("withDuration") {
        it("should accurately represent duration") {
          val f = Future {
            Thread.sleep(TestDuration)
            "hello world"
          }
          val s = Timing.withDuration(f) { (s, d) =>
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
          val s = Timing.withDurationSafe(f) { (s, d) =>
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
          val s = Timing.withDurationSafe(f) { (s, d) =>
            s.failure.exception.getMessage shouldEqual "error"
            testDuration(d)
          }.failed.futureValue
          s.getMessage shouldEqual "error"
        }
      }

    }
  }

}
