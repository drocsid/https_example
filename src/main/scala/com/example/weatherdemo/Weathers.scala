package com.example.weatherdemo

import cats.Applicative
import cats.implicits._
import cats.effect.Sync
import cats.syntax.functor._
import io.circe.Decoder
import io.circe.generic.auto._
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf
import org.http4s.client.Client
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.implicits._
import org.http4s.{EntityDecoder, EntityEncoder, Method, Uri, Request}
import org.http4s.client.Client
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.Method._
import org.http4s.circe._

object Weathers {

  //sealed
  trait Shape2

  case class Points(
                     `@context`: (String, Context),
                     id: String,
                     `type`: String,
                     geometry: Geometry1,
                     properties: Properties
                   ) extends Shape2

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
                    ) extends Shape2

  case class Geometry(
                       `@id`: String,
                       `@type`: String
                     ) extends Shape2

  case class Bearing(
                      `@type`: String
                    ) extends Shape2

  case class Value(
                    `@id`: String
                  ) extends Shape2

  case class Geometry1(
                        `type`: String,
                        coordinates: Seq[Double]
                      ) extends Shape2

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
                       ) extends Shape2

  case class RelativeLocation(
                               `type`: String,
                               geometry: Geometry2,
                               properties: Properties1
                             ) extends Shape2

  case class Geometry2(
                        `type`: String,
                        coordinates: Seq[Double]
                      ) extends Shape2

  case class Properties1(
                          city: String,
                          state: String,
                          distance: Distance,
                          bearing: Bearing1
                        ) extends Shape2

  case class Distance(
                       value: Double,
                       unitCode: String
                     ) extends Shape2

  case class Bearing1(
                       value: Double,
                       unitCode: String
                     ) extends Shape2

  trait GenericDerivation {
   def get: F[Shape2]
  }

  object GenericDerivation {
    def apply[F[_]](implicit ev: GenericDerivation[F]): GenericDerivation[F] = ev

    implicit def weatherEntityDecoder[F[_]: Sync]: EntityDecoder[F, Shape2] = jsonOf

    implicit val decodeEvent: Decoder[Shape2] =
      List[Decoder[Shape2]](
        Decoder[Points].widen,
        Decoder[Context].widen,
        Decoder[Geometry].widen,
        Decoder[Bearing].widen,
        Decoder[Value].widen,
        Decoder[Geometry1].widen,
        Decoder[Properties].widen,
        Decoder[RelativeLocation].widen,
        Decoder[Geometry2].widen,
        Decoder[Properties1].widen,
        Decoder[Distance].widen,
        Decoder[Bearing1].widen
      ).reduceLeft(_ or _)
  }
  final case class WeatherError(e: Throwable) extends RuntimeException

  def impl[F[_]: Sync](C: Client[F]): GenericDerivation[F] = new Shape2[F]{
    val dsl = new Http4sClientDsl[F]{}
    import dsl._
    def get: F[Shape2] = {
      C.expect[Shape2](GET(uri"https://api.weather.gov/points/39.7456,-97.0892"))
        .adaptError{ case t => WeatherError(t)} // Prevent Client Json Decoding Failure Leaking
    }

}

