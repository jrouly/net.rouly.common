package net.rouly.common.server.play.implicits

import play.api.libs.json.{Json, Writes}
import play.api.mvc.Result
import play.api.mvc.Results._

import scala.concurrent.{ExecutionContext, Future}

object ResultBodyImplicits {

  implicit class JsonResult[T](val t: T) extends AnyVal {

    def toJsonResult(implicit writes: Writes[T]): Result = Ok(Json.toJson(t))
  }

  implicit class AsyncJsonResult[T](val ft: Future[T]) extends AnyVal {

    def toJsonResult(implicit writes: Writes[T], executionContext: ExecutionContext): Future[Result] =
      ft.map(t => new JsonResult(t).toJsonResult)
  }

  implicit class CollectionJsonResult[T](val ts: Iterable[T]) extends AnyVal {

    def okOrNotFound(implicit writes: Writes[T]): Result =
      if (ts.isEmpty) NotFound else new JsonResult(ts).toJsonResult
  }

  implicit class AsyncCollectionJsonResult[T](val fts: Future[Iterable[T]]) extends AnyVal {

    def okOrNotFound(implicit writes: Writes[T], executionContext: ExecutionContext): Future[Result] =
      fts.map(ts => new CollectionJsonResult(ts).okOrNotFound)
  }

  implicit class OptionJsonResult[T](val maybeT: Option[T]) extends AnyVal {

    def okOrNotFound(implicit writes: Writes[T]): Result =
      maybeT match {
        case None => NotFound
        case Some(t) => new JsonResult(t).toJsonResult
      }
  }

  implicit class AsyncOptionJsonResult[T](val ft: Future[Option[T]]) extends AnyVal {

    def okOrNotFound(implicit writes: Writes[T], executionContext: ExecutionContext): Future[Result] =
      ft.map(t => new OptionJsonResult(t).okOrNotFound)
  }

}
