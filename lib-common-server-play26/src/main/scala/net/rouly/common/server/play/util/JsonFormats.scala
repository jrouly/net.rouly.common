package net.rouly.common.server.play.util

import play.api.libs.json._

trait JsonFormats {

  /**
    * Format a type as a different, already serializable type.
    * @param fToT deserialize [[F]] to [[T]]
    * @param tToF serialize [[T]] to [[F]]
    * @tparam T the input type to serialize
    * @tparam F the serializable format type, e.g. String, Double
    * @return a formatter for [[T]] backed by the formatter for [[F]]
    */
  def formatAs[T, F: Format](fToT: F => T, tToF: T => F): Format[T] =
    new Format[T] {
      override def reads(json: JsValue): JsResult[T] = json.validate[F].map(fToT)
      override def writes(o: T): JsValue = Json.toJson(tToF(o))
    }

}

object JsonFormats extends JsonFormats
