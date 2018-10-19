package net.rouly.common.database

import slick.jdbc.JdbcBackend._
import slick.jdbc.{JdbcBackend, JdbcProfile}
import slick.util.AsyncExecutor

class RemoteDatabase(
  configuration: RemoteDatabaseConfiguration,
  driver: JdbcProfile
)(implicit protected val executionContext: DatabaseExecutionContext) extends ApplicationDatabase {

  protected lazy val database: JdbcBackend.DatabaseDef = Database.forURL(
    url = configuration.url,
    user = configuration.user,
    password = configuration.password,
    driver = driver.getClass.getName,
    executor = AsyncExecutor.default()
  )
}

case class RemoteDatabaseConfiguration(
  user: String,
  password: String,
  url: String
)
