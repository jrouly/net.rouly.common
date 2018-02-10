package net.rouly.common.server.play.module

import akka.actor.ActorSystem
import com.typesafe.config.Config
import net.rouly.common.config.Configuration
import play.api.Application

import scala.concurrent.ExecutionContext

trait AppServerComponents {
  implicit def actorSystem: ActorSystem
  implicit def executionContext: ExecutionContext

  def config: Config
  def appConfig: Configuration

  def application: Application
}
