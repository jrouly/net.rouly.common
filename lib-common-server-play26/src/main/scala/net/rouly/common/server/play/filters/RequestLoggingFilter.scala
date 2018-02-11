package net.rouly.common.server.play.filters

import akka.stream.Materializer
import com.typesafe.scalalogging.StrictLogging
import net.rouly.common.server.play.filters.RequestLoggingFilter.Loggable
import net.rouly.common.util.TimingImplicits._
import play.api.libs.json.{Format, Json}
import play.api.mvc._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal
import scala.util.{Failure, Success}

class RequestLoggingFilter(
  implicit
  val mat: Materializer,
  ec: ExecutionContext
) extends Filter with StrictLogging {

  override def apply(next: RequestHeader => Future[Result])(request: RequestHeader): Future[Result] = {
    next(request).withDurationSafe {
      case (Success(result), duration) => logger.info(Loggable(request, result, duration).logString)
      case (Failure(NonFatal(ex)), duration) => logger.error(Loggable(request, ex, duration).logString)
    }
  }

}

object RequestLoggingFilter {

  private sealed trait Loggable {
    def logString: String
  }

  private case class LoggableRequestResponse(
    duration: Long,
    method: String,
    remoteAddr: String,
    status: Int,
    path: String,
    contentLength: Option[Long]
  ) extends Loggable {
    override def logString: String = Json.stringify(Json.toJson(this))
  }

  private case class LoggableRequestException(
    duration: Long,
    method: String,
    remoteAddr: String,
    status: Int = 500,
    path: String,
    exceptionClass: String,
    exceptionMessage: String
  ) extends Loggable {
    override def logString: String = Json.stringify(Json.toJson(this))
  }

  private implicit val loggableRequestResponseFormat: Format[LoggableRequestResponse] = Json.format[LoggableRequestResponse]
  private implicit val loggableRequestExceptionFormat: Format[LoggableRequestException] = Json.format[LoggableRequestException]

  private object Loggable {
    def apply(request: RequestHeader, result: Result, duration: Duration): Loggable = {
      LoggableRequestResponse(
        duration = duration.toMillis,
        method = request.method,
        remoteAddr = request.remoteAddress,
        status = result.header.status,
        path = request.uri,
        contentLength = result.body.contentLength
      )
    }

    def apply(request: RequestHeader, result: Throwable, duration: Duration): Loggable = {
      LoggableRequestException(
        duration = duration.toMillis,
        method = request.method,
        remoteAddr = request.remoteAddress,
        path = request.uri,
        exceptionClass = result.getClass.getName,
        exceptionMessage = result.getMessage
      )
    }
  }

}
