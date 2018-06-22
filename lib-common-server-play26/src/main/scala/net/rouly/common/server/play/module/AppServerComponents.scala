package net.rouly.common.server.play.module

import akka.actor.ActorSystem
import akka.stream.Materializer
import net.rouly.common.config.Configuration
import net.rouly.common.server.play.config.AppServerConfig
import net.rouly.common.server.play.filters.ApplicationHttpFilters
import play.api.Application
import play.api.mvc.{ControllerComponents, EssentialFilter}
import play.api.routing.Router

import scala.concurrent.ExecutionContext

trait AppServerComponents {

  implicit def actorSystem: ActorSystem
  implicit def executionContext: ExecutionContext
  implicit def materializer: Materializer

  def appConfig: Configuration = AppServerConfig.default

  def application: Application

  def controllerComponents: ControllerComponents

  def httpFilters: Seq[EssentialFilter] = ApplicationHttpFilters.commonFilters

  def router: Router

}
