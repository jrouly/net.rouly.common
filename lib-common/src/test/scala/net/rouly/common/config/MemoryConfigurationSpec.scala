package net.rouly.common.config

import org.scalatest.{FunSpec, Matchers}

class MemoryConfigurationSpec extends FunSpec with Matchers {

  describe("MemoryConfiguration") {
    it("should read a present property") {
      val config = MemoryConfiguration(Map("test.prop" -> "test.value"))
      config.get("test.prop", "default") shouldEqual "test.value"
    }

    it("should default for a missing value") {
      val config = MemoryConfiguration(Map.empty)
      config.get("test.prop", "default") shouldEqual "default"
    }
  }

}
