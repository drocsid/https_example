package com.example.weatherdemo

import cats.Applicative
import io.circe.generic.auto._
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import cats.effect.Sync
import cats.implicits._
import io.circe.{Decoder, Encoder, Json}
import io.circe.generic.semiauto.deriveEncoder
import org.http4s.{EntityDecoder, EntityEncoder, Method, Request, Uri}

sealed trait WeatherForecast

case class Forecast(
    `@context`: (String, Seq[Context2]),
    `type`: String,
    geometry: Geometry3,
    properties: Properties2
) extends WeatherForecast

case class Context2(
    wx: String,
    geo: String,
    unit: String,
    `@vocab`: String
) extends WeatherForecast

case class Geometry3(
    `type`: String,
    geometries: Seq[Geometries]
) extends WeatherForecast

case class Geometries(
    `type`: String,
    coordinates: Either[Seq[Double], Seq[Seq[Seq[Double]]]]
) extends WeatherForecast

case class Properties2(
    updated: String,
    units: String,
    forecastGenerator: String,
    generatedAt: String,
    updateTime: String,
    validTimes: String,
    elevation: Elevation,
    periods: Seq[Periods]
) extends WeatherForecast

case class Elevation(
    value: Double,
    unitCode: String
) extends WeatherForecast

case class Periods(
    number: Double,
    name: String,
    startTime: String,
    endTime: String,
    isDaytime: Boolean,
    temperature: Double,
    temperatureUnit: String,
    windSpeed: String,
    windDirection: String,
    icon: String,
    shortForecast: String,
    detailedForecast: String
) extends WeatherForecast

object WeatherForecast {

  implicit val decodeEvent: Decoder[WeatherForecast] =
    List[Decoder[WeatherForecast]](
      Decoder[Forecast].widen,
      Decoder[Context2].widen,
      Decoder[Geometry3].widen,
      Decoder[Geometries].widen,
      Decoder[Properties2].widen,
      Decoder[Elevation].widen,
      Decoder[Periods].widen
    ).reduceLeft(_ or _)

  implicit def weatherEntityDecoder[F[_]: Sync]
      : EntityDecoder[F, WeatherForecast] = jsonOf

  implicit val weatherEncoder: Encoder[WeatherForecast] =
    deriveEncoder[WeatherForecast]

  implicit def weatherEntityEncoder[F[_]: Applicative]
      : EntityEncoder[F, WeatherForecast] =
    jsonEncoderOf
}
