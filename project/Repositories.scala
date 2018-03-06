import sbt._
import sbt.Keys._
import java.net.URL

object Repositories {

  object RoulyNet {
    lazy val snapshot = Resolver.bintrayRepo("jrouly", "sbt-dev")
    lazy val release = Resolver.bintrayRepo("jrouly", "sbt-release")
  }

}
