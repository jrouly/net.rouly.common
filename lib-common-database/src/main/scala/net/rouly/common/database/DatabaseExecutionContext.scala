package net.rouly.common.database

import scala.concurrent.ExecutionContext

class DatabaseExecutionContext(private val underlying: ExecutionContext) extends ExecutionContext {

  override def execute(runnable: Runnable): Unit = underlying.execute(runnable)

  override def reportFailure(cause: Throwable): Unit = underlying.reportFailure(cause)

}

object DatabaseExecutionContext {

  def apply(executionContext: ExecutionContext): DatabaseExecutionContext = new DatabaseExecutionContext(executionContext)

}
