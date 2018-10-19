package net.rouly.common.database

import com.typesafe.config.{Config, ConfigFactory}
import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcBackend._

class ConfiguredDatabase(
  path: String,
  config: Config = ConfigFactory.load()
)(implicit protected val executionContext: DatabaseExecutionContext) extends ApplicationDatabase {

  protected lazy val database: JdbcBackend.DatabaseDef = Database.forConfig(path, config)

}
