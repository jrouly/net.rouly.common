package net.rouly.common.config

class PrefixedConfiguration(prefix: String, underlying: Configuration) extends Configuration {

  override def get(key: String, default: => String): String = underlying.get(s"$prefix.$key", default)
}
