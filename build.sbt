import Bintray._
import Dependencies._

name := "lib-common"

lazy val noPublish = Seq(
  publishArtifact := false,
  publishLocal := {},
  publish := {}
)

lazy val commonSettings = Seq(
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
  organization := "net.rouly",
  scalaVersion := "2.12.2",
  scalacOptions ++= Seq("-Xfatal-warnings"),
  scalacOptions in (Compile, doc) ++= Seq("-no-link-warnings"),
  version := "0.0.12",
  isSnapshot := false
) ++ bintraySettings

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(noPublish)
  .aggregate(
    `lib-common`,
    `lib-common-database`,
    `lib-common-server-play26`
  )

lazy val `lib-common` = project
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq(
    Common.logback,
    Common.scalaLogging,
    Common.scalaTest
  ))

lazy val `lib-common-database` = project
  .dependsOn(`lib-common`)
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq(
    Database.slick
  ))

lazy val `lib-common-server-play26` = project
  .dependsOn(`lib-common`)
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq(
    Common.scalaTest,
    Play26.playJson,
    Play26.playServer,
    Play26.playTest
  ))

resolvers += Resolver.bintrayRepo("jrouly", "sbt-release")
