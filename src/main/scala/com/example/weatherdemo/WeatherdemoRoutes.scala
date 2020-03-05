package com.example.weatherdemo

import io.circe.generic.auto._
import cats.effect.{IO, Sync}
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.client.Client
import org.http4s.dsl.Http4sDsl
import io.circe.optics.JsonPath._
import io.circe._
import io.circe.parser._
import cats.effect._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._

import cats.Applicative
import io.circe.generic.auto._
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import cats.effect.Sync
import cats.implicits._
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.deriveEncoder
import org.http4s.implicits._
import org.http4s.{EntityDecoder, EntityEncoder, Method, Request, Uri}
import org.http4s.client.Client
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.Method._
import org.http4s.circe._

object WeatherdemoRoutes {

  val dsl = new Http4sDsl[IO]{}
  import dsl._

  def otherRoute(C:Client[IO]) = HttpRoutes.of[IO] {
   case GET -> Root / "goodbye" / name =>
      for {
        json <- C.expect[Json](uri"https://api.weather.gov/points/33.3287,-84.375")
        resp <- Ok(json)
      } yield resp
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