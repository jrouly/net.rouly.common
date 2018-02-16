package net.rouly.common.util

import scala.language.implicitConversions

/**
  * Introduce a ternary operator because why not.
  *
  * {{{
  *  import TernaryOperator._
  *  val result = true ? "left" | 15
  *  // result = Left("left")
  * }}}
  *
  */
object TernaryOperator {
  case class PartiallyAppliedEither[L](b: Boolean, l: L) {
    def |[R](r: R): Either[L, R] = if (b) Left(l) else Right(r)
  }

  class TernaryBoolean(b: Boolean) {
    def ?[L](l: L): PartiallyAppliedEither[L] = PartiallyAppliedEither(b, l)
  }

  implicit def toTernaryBoolean(b: Boolean): TernaryBoolean = new TernaryBoolean(b)
}
