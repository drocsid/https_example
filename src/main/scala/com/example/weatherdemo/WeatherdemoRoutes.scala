package com.example.weatherdemo

import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import io.circe._
import cats.effect.Sync
import cats.implicits._
import org.http4s.implicits._
import org.http4s.client.Client

import org.http4s.circe._
import io.circe.optics.JsonPath._

object WeatherdemoRoutes {

  val path = root.properties.forecast.string

 def callbackRoute[F[_]:Sync](C:Client[F]) = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case req @ POST -> Root / "sms" / "callback" =>
        for {
          json <- req.asJson
          phoneNumber <- json.hcursor.downField("data").downField("attributes").downField("from").as[String].liftTo[F]
          locationString <-json.hcursor.downField("data").downField("attributes").downField("body").as[String].liftTo[F]
          trim = locationString.split(' ')
          pointsApiJson <- C.expect[Json](uri"https://api.weather.gov/points" / s"${trim(0)},${trim(1)}") //33.3287,-84.375")
          forecastApiUrl <- pointsApiJson.hcursor.downField("properties").downField("forecast").as[String].liftTo[F]
          forecastJson <- C.expect[Json](forecastApiUrl)
          b={
            println(forecastJson)
            println(phoneNumber)
          }
          resp <- Ok(b)
      } yield resp

    }
  }

  def otherRoute[F[_]:Sync](C:Client[F]) = {
    val dsl = new Http4sDsl[F]{}
    import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "goodbye" / lat / lon =>
      for {
        json <- C.expect[Json](uri"https://api.weather.gov/points" / s"$lat,$lon")
        url <- json.hcursor.downField("properties").downField("forecast").as[String].liftTo[F]
        json2 <- C.expect[Json](url)
        resp <- Ok(json2)
      } yield resp
    }
  }


  def weatherRoutes[F[_]: Sync](C: Client[F]) = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "weather" / lat / lon =>
        for {
          weather <- Weather.impl(C,lat,lon)
          resp <- Ok(weather)
        } yield resp
    }
  }

  def jokeRoutes[F[_]: Sync](J: Jokes[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "joke" =>
        for {
          joke <- J.get
          resp <- Ok(joke)
        } yield resp
    }
  }

  def helloWorldRoutes[F[_]: Sync](H: HelloWorld[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "hello" / name =>
        for {
          greeting <- H.hello(HelloWorld.Name(name))
          resp <- Ok(greeting)
        } yield resp
    }
  }
}