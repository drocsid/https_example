package com.example.weatherdemo

import cats.effect.Sync
import cats.syntax.functor._
import io.circe.Decoder
import io.circe.generic.auto._
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf
import org.http4s.client.Client
import org.http4s.client.dsl.Http4sClientDsl

  sealed trait Weather

  case class Points(
                     `@context`: (String, Context),//ArrayPair,
                     id: String,
                     `type`: String,
                     geometry: Geometry1,
                     properties: Properties
                   ) extends Weather

  case class Context(
                      wx: String,
                      s: String,
                      geo: String,
                      unit: String,
                      `@vocab`: String,
                      geometry: Geometry,
                      city: String,
                      state: String,
                      distance: Geometry,
                      bearing: Bearing,
                      value: Value,
                      unitCode: Geometry,
                      forecastOffice: Bearing,
                      forecastGridData: Bearing,
                      publicZone: Bearing,
                      county: Bearing
                    ) extends Weather

  case class Geometry(
                       `@id`: String,
                       `@type`: String
                     ) extends Weather

  case class Bearing(
                      `@type`: String
                    ) extends Weather

  case class Value(
                    `@id`: String
                  ) extends Weather

  case class Geometry1(
                        `type`: String,
                        coordinates: Seq[Double]
                      ) extends Weather

  case class Properties(
                         `@id`: String,
                         `@type`: String,
                         cwa: String,
                         forecastOffice: String,
                         gridX: Double,
                         gridY: Double,
                         forecast: String,
                         forecastHourly: String,
                         forecastGridData: String,
                         observationStations: String,
                         relativeLocation: RelativeLocation,
                         forecastZone: String,
                         county: String,
                         fireWeatherZone: String,
                         timeZone: String,
                         radarStation: String
                       ) extends Weather

  case class RelativeLocation(
                               `type`: String,
                               geometry: Geometry2,
                               properties: Properties1
                             ) extends Weather

  case class Geometry2(
                        `type`: String,
                        coordinates: Seq[Double]
                      ) extends Weather

  case class Properties1(
                          city: String,
                          state: String,
                          distance: Distance,
                          bearing: Bearing1
                        ) extends Weather

  case class Distance(
                       value: Double,
                       unitCode: String
                     ) extends Weather

  case class Bearing1(
                       value: Double,
                       unitCode: String
                     ) extends Weather

  object Weather {
    def apply[F[_]](implicit ev: Weather[F]): Weather[F] = ev

    implicit def weatherEntityDecoder[F[_] : Sync]: EntityDecoder[F, Weather] = jsonOf

    final case class WeatherError(e: Throwable) extends RuntimeException

    def impl[F[_] : Sync](C: Client[F]): Weather[F] = new Weather[F] {
      val dsl = new Http4sClientDsl[F] {}

      import dsl._

      def get: F[Weather] = {
        C.expect[Weather](GET(uri"https://api.weather.gov/points/39.7456,-97.0892"))
          .adaptError { case t => WeatherError(t) } // Prevent Client Json Decoding Failure Leaking

      }
    }
  }




