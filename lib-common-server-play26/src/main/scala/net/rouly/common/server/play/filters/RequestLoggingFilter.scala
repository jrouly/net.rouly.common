package net.rouly.common.server.play.filters

import akka.stream.Materializer
import com.typesafe.scalalogging.StrictLogging
import net.rouly.common.server.play.filters.RequestLoggingFilter.LoggableRequestResponse
import play.api.libs.json.{Format, Json}
import play.api.mvc._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

class RequestLoggingFilter(
  implicit
  val mat: Materializer,
  ec: ExecutionContext
) extends Filter with StrictLogging {
  override def apply(next: RequestHeader => Future[Result])(request: RequestHeader): Future[Result] = {
    timeOp(next(request)) {
      case (result, duration) =>
        val logObject = LoggableRequestResponse(request, result, duration)
        logger.info(Json.toJson(logObject).toString)
    }
  }

  private def timeOp[T, U](f: => Future[T])(op: (T, Duration) => U): Future[T] = {
    val start = System.currentTimeMillis()
    f.map { t =>
      val duration = Duration(System.currentTimeMillis() - start, MILLISECONDS)
      op(t, duration)
      t
    }
  }
}

object RequestLoggingFilter {

  case class LoggableRequestResponse(
    duration: Long,
    method: String,
    remoteAddr: String,
    status: Int,
    uri: String,
    contentLength: Option[Long]
  )

  object LoggableRequestResponse {
    def apply(request: RequestHeader, result: Result, duration: Duration): LoggableRequestResponse = {
      LoggableRequestResponse(
        duration = duration.toMillis,
        method = request.method,
        remoteAddr = request.remoteAddress,
        status = result.header.status,
        uri = request.uri,
        contentLength = result.body.contentLength
      )
    }
  }

  implicit val loggableRequestResponseFormat: Format[LoggableRequestResponse] = Json.format[LoggableRequestResponse]

}
