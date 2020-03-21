/**
  * Borrowed from https://github.com/plokhotnyuk/jsoniter-scala/blob/master/jsoniter-scala-benchmark/src/main/scala/com/github/plokhotnyuk/jsoniter_scala/benchmark/GeoJSONBenchmark.scala
  */

package com.example.weatherdemo

import io.circe.Codec

import io.circe.Decoder._
import io.circe.Encoder._
import io.circe._
import io.circe.generic.extras._
import io.circe.generic.extras.decoding.UnwrappedDecoder
import io.circe.generic.extras.encoding.UnwrappedEncoder
import io.circe.generic.extras.semiauto._

import scala.collection.immutable.{BitSet, IntMap}
import scala.collection.mutable
import scala.util.Try
import scala.collection.immutable.IndexedSeq


object GeoJSON {

 sealed trait Geometry extends Product with Serializable

 sealed trait SimpleGeometry extends Geometry

 case class Point(coordinates: (Double, Double)) extends SimpleGeometry

 case class MultiPoint(coordinates: IndexedSeq[(Double, Double)]) extends SimpleGeometry

 case class LineString(coordinates: IndexedSeq[(Double, Double)]) extends SimpleGeometry

 case class MultiLineString(coordinates: IndexedSeq[IndexedSeq[(Double, Double)]]) extends SimpleGeometry

 case class Polygon(coordinates: IndexedSeq[IndexedSeq[(Double, Double)]]) extends SimpleGeometry

 case class MultiPolygon(coordinates: IndexedSeq[IndexedSeq[IndexedSeq[(Double, Double)]]]) extends SimpleGeometry

 case class GeometryCollection(geometries: IndexedSeq[SimpleGeometry]) extends Geometry

 sealed trait GeoJSON extends Product with Serializable

 sealed trait SimpleGeoJSON extends GeoJSON

 case class Feature(properties: Map[String, String] = Map.empty,
                    geometry: Geometry,
                    bbox: Option[(Double, Double, Double, Double)] = None) extends SimpleGeoJSON

 case class FeatureCollection(
                               features: IndexedSeq[SimpleGeoJSON],
                               bbox: Option[(Double, Double, Double, Double)] = None) extends GeoJSON

}
