package net.rouly.common.server.play.config

import com.typesafe.config.{Config, ConfigFactory}
import net.rouly.common.config.Configuration
import net.rouly.common.config.decorators.{EnvironmentConfiguration, LoggingConfiguration, PlayConfiguration, PropertiesConfiguration}

class AppServerConfig(protected val config: Config)
  extends Configuration
  with PlayConfiguration
  with EnvironmentConfiguration
  with PropertiesConfiguration
  with LoggingConfiguration

object AppServerConfig {

  def default: Configuration = new AppServerConfig(ConfigFactory.defaultApplication)

}
