import Dependencies._
import Repositories._

name := "lib-common"

// Publish settings.
resolvers += RoulyNet.release
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

lazy val commonSettings = Seq(
  organization := "net.rouly",
  scalaVersion := "2.12.2",
  scalacOptions ++= Seq("-Xfatal-warnings"),
  scalacOptions in (Compile, doc) ++= Seq("-no-link-warnings"),
  version := "0.0.1",
  isSnapshot := false
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(noPublishSettings)
  .aggregate(
    `lib-common`,
    `lib-common-server-play26`
  )

lazy val `lib-common` = project
  .settings(commonSettings)
  .settings(publishSettings)
  .settings(libraryDependencies ++= Seq(
    Common.logback,
    Common.scalaLogging,
    Common.scalaTest
  ))

lazy val `lib-common-server-play26` = project
  .dependsOn(`lib-common`)
  .settings(commonSettings)
  .settings(publishSettings)
  .settings(libraryDependencies ++= Seq(
    Play26.playJson,
    Play26.playServer,
    Play26.playTest
  ))
