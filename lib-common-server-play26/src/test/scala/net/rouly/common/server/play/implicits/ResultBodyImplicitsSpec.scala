package net.rouly.common.server.play.implicits

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import net.rouly.common.server.play.implicits.ResultBodyImplicits._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSpec, Matchers}
import play.api.http.Status._
import play.api.libs.json.{Json, Writes}
import play.api.mvc.Result

import scala.concurrent.{ExecutionContext, Future}

class ResultBodyImplicitsSpec
  extends FunSpec
  with ScalaFutures
  with Matchers {

  case class Body(text: String)
  implicit val writesBody: Writes[Body] = Json.writes[Body]

  implicit val actorSystem: ActorSystem = ActorSystem.apply("test")
  implicit val materializer: Materializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = ExecutionContext.Implicits.global

  val body = Body("hello")

  describe("ResponseBodyImplicits") {

    describe("JsonResponse") {
      it("should turn an object with a writes into a 200 OK json string") {
        val response = body.toJsonResult
        status(response) shouldEqual OK
        body(response) shouldEqual Json.stringify(Json.toJson(body))
      }
    }

    describe("AsyncJsonResponse") {
      it("should eventually turn an object with a writes into a 200 OK json string") {
        val response = Future.successful(body).toJsonResult.futureValue
        status(response) shouldEqual OK
        body(response) shouldEqual Json.stringify(Json.toJson(body))
      }
    }

    describe("JsonResponseCollection") {
      it("should turn a collection of objects with a writes into a 200 OK json string") {
        val response = List(body, body).okOrNotFound
        status(response) shouldEqual OK
        body(response) shouldEqual Json.stringify(Json.toJson(List(body, body)))
      }

      it("should turn an empty collection of objects with a writes into a 404") {
        val response = List.empty[Body].okOrNotFound
        status(response) shouldEqual NOT_FOUND
        body(response) shouldBe empty
      }
    }

    describe("AsyncJsonResponseCollection") {
      it("should eventually turn a collection of objects with a writes into a 200 OK json string") {
        val response = Future.successful(List(body, body)).okOrNotFound.futureValue
        status(response) shouldEqual OK
        body(response) shouldEqual Json.stringify(Json.toJson(List(body, body)))
      }

      it("should eventually turn an empty collection of objects with a writes into a 404") {
        val response = Future.successful(List.empty[Body]).okOrNotFound.futureValue
        status(response) shouldEqual NOT_FOUND
        body(response) shouldBe empty
      }
    }

    describe("JsonResponseOption") {
      it("should turn an option of an object with a writes into a 200 OK json string") {
        val response = Some(body).okOrNotFound
        status(response) shouldEqual OK
        body(response) shouldEqual Json.stringify(Json.toJson(body))
      }

      it("should turn None of objects with a writes into a 404") {
        val response = (None: Option[Body]).okOrNotFound
        status(response) shouldEqual NOT_FOUND
        body(response) shouldBe empty
      }
    }

    describe("AsyncJsonResponseOption") {
      it("should eventually turn an option of an object with a writes into a 200 OK json string") {
        val response = Future.successful(Some(body)).okOrNotFound.futureValue
        status(response) shouldEqual OK
        body(response) shouldEqual Json.stringify(Json.toJson(body))
      }

      it("should eventually turn None of objects with a writes into a 404") {
        val response = Future.successful(None: Option[Body]).okOrNotFound.futureValue
        status(response) shouldEqual NOT_FOUND
        body(response) shouldBe empty
      }
    }

  }

  private def body(response: Result): String = response.body.consumeData.futureValue.utf8String

  private def status(response: Result): Int = response.header.status
}
