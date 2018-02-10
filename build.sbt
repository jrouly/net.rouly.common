import Dependencies._
import Repositories._

name := "lib-common"

// Publish settings.
resolvers += RoulyNet.release

lazy val commonSettings = Seq(
  organization := "net.rouly",
  scalaVersion := "2.12.2",
  scalacOptions ++= Seq("-Xfatal-warnings"),
  scalacOptions in (Compile, doc) ++= Seq("-no-link-warnings"),
  version := "0.0.3-SNAPSHOT",
  isSnapshot := true
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(noPublish)
  .aggregate(
    `lib-common`,
    `lib-common-server-play26`
  )

lazy val `lib-common` = project
  .settings(commonSettings)
  .settings(RoulyNet.publish)
  .settings(libraryDependencies ++= Seq(
    Common.logback,
    Common.scalaLogging,
    Common.scalaTest
  ))

lazy val `lib-common-server-play26` = project
  .dependsOn(`lib-common`)
  .settings(commonSettings)
  .settings(RoulyNet.publish)
  .settings(libraryDependencies ++= Seq(
    Play26.playJson,
    Play26.playServer,
    Play26.playTest
  ))
