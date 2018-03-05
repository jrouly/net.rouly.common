package net.rouly.common.server.play.implicits

import play.api.libs.json.{Json, Writes}
import play.api.mvc.Result
import play.api.mvc.Results._

import scala.concurrent.{ExecutionContext, Future}

object ResultBodyImplicits {

  implicit class JsonResult[T](val t: T) extends AnyVal {

    def toJsonResult(implicit writes: Writes[T]): Result =
      Ok(Json.toJson(t))
  }

  implicit class AsyncJsonResult[T](val ft: Future[T]) extends AnyVal {

    def toJsonResult(implicit writes: Writes[T], executionContext: ExecutionContext): Future[Result] =
      ft.map(t => Ok(Json.toJson(t)))
  }

  implicit class JsonResultCollection[T](val ts: Iterable[T]) extends AnyVal {

    def okOrNotFound(implicit writes: Writes[T]): Result =
      if (ts.isEmpty) NotFound else ts.toJsonResult
  }

  implicit class AsyncJsonResultCollection[T](val fts: Future[Iterable[T]]) extends AnyVal {

    def okOrNotFound(implicit writes: Writes[T], executionContext: ExecutionContext): Future[Result] =
      fts.map(ts => ts.okOrNotFound)
  }

  implicit class JsonResultOption[T](val maybeT: Option[T]) extends AnyVal {

    def okOrNotFound(implicit writes: Writes[T]): Result =
      maybeT match {
        case None => NotFound
        case Some(t) => t.toJsonResult
      }
  }

  implicit class AsyncJsonResultOption[T](val ft: Future[Option[T]]) extends AnyVal {

    def okOrNotFound(implicit writes: Writes[T], executionContext: ExecutionContext): Future[Result] =
      ft.map(t => t.okOrNotFound)
  }

}
