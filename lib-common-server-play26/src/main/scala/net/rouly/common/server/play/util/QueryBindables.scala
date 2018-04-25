package net.rouly.common.server.play.util

import play.api.mvc.{PathBindable, QueryStringBindable}

trait QueryBindables {

  /**
    * @param name human readable name of the type of [[T]]
    * @param parse deserialize a path parameter as a [[T]]
    * @param serialize serialize a [[T]] as a path parameter
    */
  def pathBinder[T](name: String, parse: String => T, serialize: T => String): PathBindable.Parsing[T] =
    new PathBindable.Parsing[T](parse, serialize, errorHandler(name))

  /**
    * @param name human readable name of the type of [[T]]
    * @param parse deserialize a query parameter as a [[T]]
    * @param serialize serialize a [[T]] as a query parameter
    */
  def queryStringBinder[T](name: String, parse: String => T, serialize: T => String): QueryStringBindable.Parsing[T] =
    new QueryStringBindable.Parsing[T](parse, serialize, errorHandler(name))

  /**
    * @param name human readable name of the type of [[T]]
    * @param parse deserialize a parameter as a [[T]]
    * @param serialize serialize a [[T]] as a parameter
    */
  def binders[T](name: String, parse: String => T, serialize: T => String): (PathBindable[T], QueryStringBindable[T]) =
    (pathBinder[T](name, parse, serialize), queryStringBinder[T](name, parse, serialize))

  private def errorHandler(name: String)(key: String, e: Exception): String =
    s"Cannot parse parameter $key as $name: ${e.getLocalizedMessage}"

}

object QueryBindables extends QueryBindables
