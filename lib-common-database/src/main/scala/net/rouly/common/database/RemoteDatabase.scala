package net.rouly.common.database

import net.rouly.common.config.Configuration
import slick.jdbc.JdbcBackend._
import slick.jdbc.{JdbcBackend, JdbcProfile}
import slick.util.AsyncExecutor

import scala.concurrent.ExecutionContext

class RemoteDatabase(
  configuration: Configuration,
  driver: JdbcProfile,
  name: String = "default"
)(implicit protected val executionContext: ExecutionContext) extends ApplicationDatabase {

  private val user = configuration.get(s"db.$name.username", "postgres")
  private val password = configuration.get(s"db.$name.password", "")

  private val host = configuration.get(s"db.$name.host", "localhost")
  private val port = configuration.getInt(s"db.$name.port", 5432)
  private val url = s"jdbc:postgresql://$host:$port/$name"

  private val keepAlive = configuration.getBoolean("database.keepAlive", false)

  protected lazy val database: JdbcBackend.DatabaseDef = Database.forURL(
    url = url,
    user = user,
    password = password,
    driver = driver.getClass.getName,
    executor = AsyncExecutor.default(),
    keepAliveConnection = keepAlive
  )
}
