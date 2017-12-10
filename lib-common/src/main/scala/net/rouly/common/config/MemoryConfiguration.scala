package net.rouly.common.config

/**
  * Read configuration data from a static in-memory map. Primarily good for testing.
  */
class MemoryConfiguration(memory: Map[String, String]) extends Configuration {

  override def get(key: String, default: => String): String = memory.getOrElse(key, default)

}

object MemoryConfiguration {
  def apply(memory: Map[String, String]): Configuration = new MemoryConfiguration(memory)
}
