package net.rouly.common.util

import org.scalatest.Matchers

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

trait TimingFixtures extends Matchers {

  implicit val ec = ExecutionContext.Implicits.global

  val TestDuration = 10
  def testDuration(d: Duration, expected: Int = TestDuration - 1) = d.toMillis.toInt should be >= expected

}
