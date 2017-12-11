# lib-common

## Configuration

Multisourced configuration infrastructure utilizing the [stackable trait pattern](http://www.artima.com/scalazine/articles/stackable_trait_pattern.html).

Provided implementations can pull from environment variables, JVM system properties or in-memory maps.
An implementation to pull from Typesafe configs is present in the `lib-common-server-play26` package.

```scala
class MyConfig extends Configuration
  with EnvironmentConfiguration
  with PropertiesConfiguration
  with LoggingConfiguration
```
