import Dependencies._

name := "lib-common"

lazy val commonSettings = Seq(
  isSnapshot := true,
  organization := "net.rouly",
  scalaVersion := "2.12.2",
  scalacOptions ++= Seq("-Xfatal-warnings"),
  scalacOptions in (Compile, doc) ++= Seq("-no-link-warnings"),
  version := "0.0.1"
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .aggregate(
    `lib-common`,
    `lib-common-server`,
    `lib-common-server-play26`
  )

lazy val `lib-common` = project
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq(
    Common.logback,
    Common.scalaLogging,
    Common.scalaTest
  ))

lazy val `lib-common-server` = project
  .dependsOn(`lib-common`)
  .settings(commonSettings)

lazy val `lib-common-server-play26` = project
  .enablePlugins(PlayScala)
  .dependsOn(`lib-common-server`)
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq(
    Play26.playJson,
    Play26.playServer,
    Play26.playTest
  ))
