package net.rouly.common.server.play.filters

import akka.stream.Materializer
import play.api.http.HttpFilters
import play.api.mvc.EssentialFilter

import scala.concurrent.ExecutionContext

class ApplicationHttpFilters(val filters: EssentialFilter*) extends HttpFilters

object ApplicationHttpFilters {

  def apply(filters: EssentialFilter*): ApplicationHttpFilters = new ApplicationHttpFilters(filters: _*)

  def commonFilters(
    implicit
    mat: Materializer,
    executionContext: ExecutionContext
  ): List[EssentialFilter] = List(new RequestLoggingFilter)

}
