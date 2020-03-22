package com.example.weatherdemo

import io.circe.Codec
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

object WeatherAPI {

  case class Context(
                      wx: String,
                      geo: String,
                      unit: String,
                      `@vocab`: String)

  case class ForecastProperties(
                                 updated: String,
                                 units: String,
                                 forecastGenerator: String,
                                 generatedAt: String,
                                 updateTime: String,
                                 validTimes: String,
                                 elevation: Elevation,
                                 periods: IndexedSeq[Periods])

  case class Elevation(
                        value: Double,
                        unitCode: String)

  case class Periods(
                      number: Double,
                      name: String,
                      startTime: String,
                      endTime: String,
                      isDaytime: Boolean,
                      temperature: Double,
                      temperatureUnit: String,
                      temperatureTrend: Option[String] = None,
                      windSpeed: String,
                      windDirection: String,
                      icon: String,
                      shortForecast: String,
                      detailedForecast: String)

  sealed trait Geometry extends Product with Serializable

  sealed trait SimpleGeometry extends Geometry

  case class Point(coordinates: (Double, Double)) extends SimpleGeometry

  case class MultiPoint(coordinates: IndexedSeq[(Double, Double)]) extends SimpleGeometry

  case class LineString(coordinates: IndexedSeq[(Double, Double)]) extends SimpleGeometry

  case class MultiLineString(coordinates: IndexedSeq[IndexedSeq[(Double, Double)]]) extends SimpleGeometry

  case class Polygon(coordinates: IndexedSeq[IndexedSeq[(Double, Double)]]) extends SimpleGeometry

  case class MultiPolygon(coordinates: IndexedSeq[IndexedSeq[IndexedSeq[(Double, Double)]]]) extends SimpleGeometry

  case class GeometryCollection(geometries: IndexedSeq[SimpleGeometry]) extends Geometry

  case class Forecast(
                       `@context`: (String, Context),
                       `type`: String = "Feature",
                       geometry: Geometry,
                       properties: ForecastProperties)

  implicit val config: Configuration = Configuration.default.withDefaults.withDiscriminator("type")

  implicit val weatherAPIC3c: Codec[WeatherAPI.Forecast] = {
    import io.circe.generic.auto._

    implicit val c1: Codec[WeatherAPI.SimpleGeometry] = deriveConfiguredCodec[WeatherAPI.SimpleGeometry]
    implicit val c2: Codec[WeatherAPI.Geometry] = deriveConfiguredCodec[WeatherAPI.Geometry]
    deriveConfiguredCodec[WeatherAPI.Forecast]
  }


}
