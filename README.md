# lib-common

[![Build Status](https://jenkins.rouly.net/buildStatus/icon?job=net.rouly.common/master)](https://jenkins.rouly.net/job/net.rouly.common/job/master/)
[![Download](https://api.bintray.com/packages/jrouly/sbt-release/lib-common/images/download.svg)](https://bintray.com/jrouly/sbt-release/lib-common/_latestVersion)

Common code and components I've reused.
Versioned together.

## Installation

```scala
resolvers += Resolver.bintrayRepo("jrouly", "sbt-release")
```

## Packages

### lib-common

Common code with no significant dependencies.

```scala
libraryDependencies += "net.rouly" % "lib-common" % "x.x.x"
```

### lib-common-database

Database interaction code with dependency on [Slick](http://slick.lightbend.com).

```scala
libraryDependencies += "net.rouly" % "lib-common-database" % "x.x.x"
```

### lib-common-server-play26

Common server-side code with dependency on [Play 2.6](https://www.playframework.com/documentation/2.6.x/Home).

```scala
libraryDependencies += "net.rouly" % "lib-common-server-play26" % "x.x.x"
```
