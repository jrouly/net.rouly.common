package net.rouly.common.database

import com.typesafe.scalalogging.StrictLogging
import net.rouly.common.util.TimingImplicits.TimeableFuture
import slick.dbio.{DBIOAction, NoStream}
import slick.jdbc.JdbcBackend

import scala.concurrent.Future
import scala.util.{Failure, Success}

trait ApplicationDatabase extends StrictLogging {

  protected implicit def executionContext: DatabaseExecutionContext

  /**
    * Underlying slick database instance.
    */
  protected def database: JdbcBackend.DatabaseDef

  /**
    * Run the database action, but emit logging about the success and duration of the interaction.
    */
  def runWithLogging[R](name: String)(action: DBIOAction[R, NoStream, Nothing]): Future[R] = {
    database.run(action).withDurationSafe {
      case (Success(_), duration) => logger.trace(s"""Executed db query "$name" duration=${duration.toMillis.toString}ms""")
      case (Failure(ex), duration) => logger.warn(s"""Executed db query "$name" duration=${duration.toMillis.toString}ms""", ex)
    }
  }

  def run[R](action: DBIOAction[R, NoStream, Nothing]): Future[R] = database.run(action)

}
