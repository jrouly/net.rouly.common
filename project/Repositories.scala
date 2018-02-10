import sbt._
import sbt.Keys._
import java.net.URL

object Repositories {

  lazy val noPublish = Seq(
    publishArtifact := false,
    publishLocal := {},
    publish := {}
  )

  object RoulyNet {
    private lazy val base = "https://artifacts.rouly.net/artifactory"

    lazy val snapshot = resolverArtifactory("sbt-dev", base)
    lazy val release = resolverArtifactory("sbt-release", base)

    lazy val publish = publishSettings(snapshot, release)
  }

  private def publishSettings(snapshot: Resolver, release: Resolver) = {
    publishTo := {
      if (isSnapshot.value) Some(snapshot)
      else Some(release)
    }
  }

  private def resolverUrl(id: String, url: String): Resolver =
    Resolver.url(id, new URL(url))(Resolver.ivyStylePatterns)

  private def resolverArtifactory(id: String, url: String): Resolver =
    "Artifactory Realm" at s"$url/$id"

}
