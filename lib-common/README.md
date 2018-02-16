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

## Timing

`TimingImplicit` introduces functionality for measuring execution time of operations.
Supports exception handling (through `Try`) as well.

```scala
val op: (A => B) = ???
val (b, duration) = op.timedApply(a)
val (tryB, duration) = op.timedApplySafe(a)
```

```scala
val f: Future[T] = ???
f.withDuration((t, duration) => println(duration))
f.withDurationSafe((tryT, duration) => println(duration))
```

## Ternary Operator

`TernaryOperator` implements a basic scala ternary operator.

```scala
import TernaryOperator._
true ? 15 | "foo"
```
