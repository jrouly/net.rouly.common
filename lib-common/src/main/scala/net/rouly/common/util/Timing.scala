package net.rouly.common.util

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

trait Timing {

  def timed[S, T](op: S => T)(s: S)(post: Duration => Any): T = {
    val start = System.nanoTime()
    val t = op(s)
    val stop = System.nanoTime()
    val duration = (stop - start).nano
    post(duration)
    t
  }

  def withDuration[S, T](op: S => T)(s: S): (T, Duration) = {
    val start = System.nanoTime()
    val t = op(s)
    val stop = System.nanoTime()
    val duration = (stop - start).nano
    (t, duration)
  }

  def timed[T](f: Future[T])(post: Duration => Any)(implicit ec: ExecutionContext): Future[T] = {
    val start = System.nanoTime()
    f.map { t =>
      val stop = System.nanoTime()
      val duration = (stop - start).nano
      post(duration)
      t
    }
  }

}

object Timing extends Timing
