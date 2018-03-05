package net.rouly.common.config.decorators

import com.typesafe.config.Config
import net.rouly.common.config.Configuration

import scala.util.Try

trait PlayConfiguration extends Configuration {

  protected def config: Config

  abstract override def get(key: String, default: => String): String = {
    val found = super.get(key, default)
    if (found == default) getPlayConfig(key, default)
    else found
  }

  private def getPlayConfig(key: String, default: => String): String =
    Try(config.resolve().getString(key)).getOrElse(default)

}
