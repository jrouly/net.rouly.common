package net.rouly.common.server.play.actions

import play.api.mvc.{ActionFunction, Result}

import scala.concurrent.Future

trait PartialActionFunction[-R[_], +P[_]] extends ActionFunction[R, P] {
  self =>

  def isDefinedAt[A](request: R[A]): Boolean

  def orElse[R1[a] <: R[a], P1[a] >: P[a]](that: PartialActionFunction[R1, P1]): PartialActionFunction[R1, P1] =
    new PartialActionFunction[R1, P1] {

      override def isDefinedAt[A](request: R1[A]): Boolean =
        self.isDefinedAt[A](request) || that.isDefinedAt[A](request)

      override def invokeBlock[A](
        request: R1[A],
        block: P1[A] => Future[Result]
      ): Future[Result] = {
        if (self.isDefinedAt(request)) self.invokeBlock(request, block)
        else that.invokeBlock(request, block)
      }

    }

}
