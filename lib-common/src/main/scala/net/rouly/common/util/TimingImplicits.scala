package net.rouly.common.util

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

object TimingImplicits {
  implicit class TimeableOp[S, T](val op: S => T) extends AnyVal {
    def timedApply(s: S)(post: Duration => Any): T = Timing.timed(op)(s)(post)
    def applyWithDuration(s: S): (T, Duration) = Timing.withDuration(op)(s)
  }

  implicit class TimeableFuture[T](val f: Future[T]) extends AnyVal {
    def timed(post: Duration => Any)(implicit ec: ExecutionContext): Future[T] = Timing.timed(f)(post)
  }
}

