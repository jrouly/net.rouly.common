import sbt._

object Dependencies {

  val macwireVersion = "2.3.0"

  object Common {

    lazy val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
    lazy val macwireMacros = "com.softwaremill.macwire" %% "macros" % macwireVersion
    lazy val macwireUtil = "com.softwaremill.macwire" %% "util" % macwireVersion
    lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"
    lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % "test"

  }

  object Play26 {

    val playVersion = "2.6.5"

    lazy val playJson = "com.typesafe.play" %% "play-json" % playVersion
    lazy val playTest = "com.typesafe.play" %% "play-test" % playVersion % "test"
    lazy val playServer = "com.typesafe.play" %% "play-server" % playVersion

  }

}
