package com.example.weatherdemo

import cats.effect.Sync
import cats.syntax.functor._
import io.circe.Decoder
import io.circe.generic.auto._
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf

object Weather {

  sealed trait Shape2

  case class Points(
                     `@context`: (String, Context),//ArrayPair,
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


  object GenericDerivation {

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

}

