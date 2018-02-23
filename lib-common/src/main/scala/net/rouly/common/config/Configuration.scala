package net.rouly.common.config

import net.rouly.common.config.decorators.{EnvironmentConfiguration, LoggingConfiguration, PropertiesConfiguration}

/**
  * Generic configuration interface.
  */
abstract class Configuration {

  /**
    * Retrieve a dot delimited configuration value.
    * @param key dot (".") delimited configuration key path
    * @param default a default value in case the configuration key path is not explicitly set
    * @return either the retrieved configured value, or the passed in default value
    */
  def get(key: String, default: => String): String = default

  /**
    * @throws IllegalArgumentException If the identified configuration cannot be parsed as a boolean.
    */
  final def getBoolean(key: String, default: => Boolean): Boolean = getT(key, default)(_.toBoolean)

  /**
    * @throws IllegalArgumentException If the identified configuration cannot be parsed as an Int.
    */
  final def getInt(key: String, default: => Int): Int = getT(key, default)(_.toInt)

  final def sub(prefix: String): Configuration = new PrefixedConfiguration(prefix, this)

  final protected def getT[T](key: String, default: => T)(to: String => T, from: T => String = (t: T) => t.toString): T =
    to(get(key, from(default)))

}

object Configuration {
  val default: Configuration =
    new Configuration with EnvironmentConfiguration with PropertiesConfiguration with LoggingConfiguration
}
