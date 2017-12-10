package net.rouly.common.config.decorators

import net.rouly.common.config.Configuration

trait EnvironmentConfiguration extends Configuration {

  abstract override def get(key: String, default: => String): String = {
    val found = super.get(key, default)
    if (found == default) getEnvVar(key, default)
    else found
  }

  private def getEnvVar(key: String, default: => String): String = {
    val env = keyAsEnvVar(key)
    Option(System.getenv(env)).getOrElse(default)
  }

  private def keyAsEnvVar(key: String): String = key.replaceAllLiterally(".", "_").toUpperCase

}
