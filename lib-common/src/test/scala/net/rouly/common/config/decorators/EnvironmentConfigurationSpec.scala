package net.rouly.common.config.decorators

import net.rouly.common.config.Configuration
import org.scalatest.{FunSpec, Matchers}

class EnvironmentConfigurationSpec extends FunSpec with Matchers {

  describe("EnvironmentConfiguration") {
    val config = new Configuration with EnvironmentConfiguration

    it("should read a real environment variable") {
      val expected = System.getenv("HOME") // required to be set by POSIX
      config.get("home", "fake_home") shouldEqual expected
    }

    it("should default for a non-existant environment variable") {
      config.get("fake.test.prop", "default") shouldEqual "default"
    }
  }

}
