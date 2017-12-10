package net.rouly.common.config.decorators

import net.rouly.common.config.Configuration

trait PropertiesConfiguration extends Configuration {

  abstract override def get(key: String, default: => String): String = {
    val found = super.get(key, default)
    if (found == default) System.getProperty(key, default)
    else found
  }

}
