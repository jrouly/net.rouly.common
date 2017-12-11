# lib-common-server-play26

## Configuration

Provides a `Configuration` implementation to read from Typesafe configs.

```scala
import com.typesafe.config.{Config => TypesafeConfig}
class MyConfig(protected val config: TypesafeConfig)
  extends Configuration
  with EnvironmentConfiguration
  with PropertiesConfiguration
  with LoggingConfiguration
```

## Play Application Components

The `AppServerLoader` provides an entry point into the Play 2.6 web application infrastructure.

```scala
class MyAppLoader extends AppServerLoader {
  def buildComponents(context: Context): MyAppComponents =
    new BuiltInComponentsFromContext(context) with MyAppComponents
}

trait MyAppComponents {
  override def router = ???
  override def httpFilters = ???
  override def config = ???
  override def appConfig = ???
}
```
