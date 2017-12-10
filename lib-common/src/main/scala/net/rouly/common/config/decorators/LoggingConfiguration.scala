package net.rouly.common.config.decorators

import com.typesafe.scalalogging.StrictLogging
import net.rouly.common.config.Configuration

trait LoggingConfiguration extends Configuration with StrictLogging {

  abstract override def get(key: String, default: => String): String = {
    val found = super.get(key, default)
    logger.info(s"$key = $found")
    found
  }

}
