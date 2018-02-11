package net.rouly.common.util

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import scala.util.Try

object TimingImplicits {

  implicit class TimeableOp[S, T](val op: S => T) extends AnyVal {
    def timedApply(s: S): (T, Duration) = Timing.withDuration(op(s))
    def timedApplySafe(s: S): (Try[T], Duration) = Timing.withDurationSafe(op(s))
  }

  implicit class TimeableFuture[T](val f: Future[T]) extends AnyVal {
    def withDuration[U](post: (T, Duration) => U)(implicit ec: ExecutionContext): Future[T] = Timing.withDuration(f)(post)
    def withDurationSafe[U](post: (Try[T], Duration) => U)(implicit ec: ExecutionContext): Future[T] = Timing.withDurationSafe(f)(post)
  }
}

