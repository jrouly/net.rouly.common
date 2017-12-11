package net.rouly.common.server.play.filters

import akka.stream.Materializer
import play.api.http.DefaultHttpFilters
import play.api.mvc._

import scala.concurrent.ExecutionContext

class ApplicationHttpFilters(filters: EssentialFilter*) extends DefaultHttpFilters(filters: _*)

object ApplicationHttpFilters {
  def commonFilters(implicit mat: Materializer, executionContext: ExecutionContext): List[EssentialFilter] = List(
    new RequestLoggingFilter
  )

  class CommonHttpFilters(implicit mat: Materializer, executionContext: ExecutionContext)
    extends ApplicationHttpFilters(commonFilters: _*)
}
