package net.rouly.common.config

import org.scalatest.{FunSpec, Matchers}

class PrefixedConfigurationSpec extends FunSpec with Matchers {

  describe("PrefixedConfiguration") {
    val memory = Map(
      "global.prefix.config.a" -> "a",
      "config.b" -> "b"
    )

    val config = new PrefixedConfiguration("global.prefix", new MemoryConfiguration(memory))

    it("should read a prefixed config") {
      config.get("config.a", "default") shouldEqual "a"
    }

    it("should not find non-prefixed configs") {
      config.get("config.b", "default") should not equal "b"
    }

  }

}
