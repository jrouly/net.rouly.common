package net.rouly.common.config.decorators

import net.rouly.common.config.Configuration
import org.scalatest.{FunSpec, Matchers}

class PropertiesConfigurationSpec extends FunSpec with Matchers {

  describe("PropertiesConfiguration") {
    val config = new Configuration with PropertiesConfiguration

    it("should read a system property") {
      System.setProperty("test.prop", "expected")
      config.get("test.prop", "default") shouldEqual "expected"
    }

    it("should default for a non-existant environment variable") {
      config.get("fake.test.prop", "default") shouldEqual "default"
    }
  }

}
