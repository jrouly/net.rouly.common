package net.rouly.common.util

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

trait Timing {

  def withDuration[T](block: => T): (T, Duration) = {
    val start = System.nanoTime()
    val t = block
    val stop = System.nanoTime()
    val duration = (stop - start).nano
    (t, duration)
  }

  def withDurationSafe[T](block: => T): (Try[T], Duration) = {
    val start = System.nanoTime()
    val t = Try(block)
    val stop = System.nanoTime()
    val duration = (stop - start).nano
    (t, duration)
  }

  def withDuration[T, U](f: Future[T])(post: (T, Duration) => U)(implicit ec: ExecutionContext): Future[T] = {
    val start = System.nanoTime()
    f.map { t =>
      val stop = System.nanoTime()
      val duration = (stop - start).nano
      post(t, duration)
      t
    }
  }

  def withDurationSafe[T, U](f: Future[T])(post: (Try[T], Duration) => U)(implicit ec: ExecutionContext): Future[T] = {
    val start = System.nanoTime()
    f.andThen {
      case t =>
        val stop = System.nanoTime()
        val duration = (stop - start).nano
        post(t, duration)
        t
    }
  }

}

object Timing extends Timing
