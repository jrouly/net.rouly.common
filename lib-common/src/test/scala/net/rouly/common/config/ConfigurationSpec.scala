package net.rouly.common.config

import net.rouly.common.config.decorators._
import org.scalatest.{FunSpec, Matchers}

class ConfigurationSpec extends FunSpec with Matchers {

  describe("Configuration") {

    describe("multiple decorators") {

      it("should read from the environment mixin") {
        val config = new Configuration with EnvironmentConfiguration with PropertiesConfiguration

        val expected = System.getenv("HOME")
        System.setProperty("home", "fake_home")
        config.get("home", "default") shouldEqual expected
      }

      it("should fall through to the property mixin") {
        val config = new Configuration with EnvironmentConfiguration with PropertiesConfiguration

        System.setProperty("test.prop", "expected")
        config.get("test.prop", "default") shouldEqual "expected"
      }

      it("should read from the property mixin") {
        val config = new Configuration with PropertiesConfiguration with EnvironmentConfiguration

        System.setProperty("home", "expected")
        config.get("home", "default") shouldEqual "expected"
      }

      it("should fall through to the environment mixin") {
        val config = new Configuration with PropertiesConfiguration with EnvironmentConfiguration

        val expected = System.getenv("HOME")
        System.clearProperty("home")
        config.get("home", "default") shouldEqual expected
      }

    }

    describe("getInt") {
      it("should get a valid int") {
        val config = MemoryConfiguration(Map("test.prop" -> "1"))
        config.getInt("test.prop", 0) shouldEqual 1
      }

      it("should throw for an invalid configuration") {
        val config = MemoryConfiguration(Map("test.prop" -> "cat"))
        intercept[IllegalArgumentException](config.getInt("test.prop", 0))
      }
    }

    describe("getBoolean") {
      it("should get a valid boolean") {
        val config = MemoryConfiguration(Map("test.prop" -> "true"))
        config.getBoolean("test.prop", false) shouldEqual true
      }

      it("should throw for an invalid configuration") {
        val config = MemoryConfiguration(Map("test.prop" -> "cat"))
        intercept[IllegalArgumentException](config.getBoolean("test.prop", false))
      }
    }

    describe("sub") {
      it("should get sub-configurations") {
        val config = MemoryConfiguration(Map("a.b.c" -> "foo", "c" -> "bar")).sub("a.b")
        config.get("c", "default") shouldEqual "foo"
      }
    }

  }
}
