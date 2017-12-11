package net.rouly.common.server.play.module

import play.api.ApplicationLoader.Context
import play.api.{ApplicationLoader, _}

trait AppServerLoader extends ApplicationLoader {

  def buildComponents(context: Context): AppServerComponents

  override def load(context: ApplicationLoader.Context): Application = {
    // set up logger
    LoggerConfigurator(context.environment.classLoader).foreach {
      _.configure(context.environment, context.initialConfiguration, Map.empty)
    }

    val components = buildComponents(context)

    components.application
  }
}
