package net.rouly.common.server.play.implicits

import play.api.libs.json.{Json, Writes}
import play.api.mvc.Result
import play.api.mvc.Results._

import scala.concurrent.{ExecutionContext, Future}

object ResponseBodyImplicits {

  implicit class JsonResponse[T](val t: T) extends AnyVal {

    def toJsonResponse(implicit writes: Writes[T]): Result =
      Ok(Json.toJson(t))
  }

  implicit class AsyncJsonResponse[T](val ft: Future[T]) extends AnyVal {

    def toJsonResponse(implicit writes: Writes[T], executionContext: ExecutionContext): Future[Result] =
      ft.map(t => Ok(Json.toJson(t)))
  }

  implicit class JsonResponseCollection[T](val ts: Iterable[T]) extends AnyVal {

    def okOrNotFound(implicit writes: Writes[T]): Result =
      if (ts.isEmpty) NotFound else ts.toJsonResponse
  }

  implicit class AsyncJsonResponseCollection[T](val fts: Future[Iterable[T]]) extends AnyVal {

    def okOrNotFound(implicit writes: Writes[T], executionContext: ExecutionContext): Future[Result] =
      fts.map(ts => ts.okOrNotFound)
  }

  implicit class JsonResponseOption[T](val maybeT: Option[T]) extends AnyVal {

    def okOrNotFound(implicit writes: Writes[T]): Result =
      maybeT match {
        case None => NotFound
        case Some(t) => t.toJsonResponse
      }
  }

  implicit class AsyncJsonResponseOption[T](val ft: Future[Option[T]]) extends AnyVal {

    def okOrNotFound(implicit writes: Writes[T], executionContext: ExecutionContext): Future[Result] =
      ft.map(t => t.okOrNotFound)
  }

}
