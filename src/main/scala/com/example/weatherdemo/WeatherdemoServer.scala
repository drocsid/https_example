package com.example.weatherdemo

import cats.effect.{ConcurrentEffect, ContextShift, IO, Timer}
import cats.implicits._
import fs2.Stream
import javax.net.ssl.SSLContext
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger

import scala.concurrent.ExecutionContext.global

object WeatherdemoServer {

  def stream[F[_]: ConcurrentEffect](
      implicit T: Timer[F],
      C: ContextShift[F]
  ): Stream[F, Nothing] = {
    for {
      client <- BlazeClientBuilder[F](global)
        .withSslContext(SSLContext.getDefault())
        .stream
      //ioclient <- BlazeClientBuilder[IO](global).stream
      helloWorldAlg = HelloWorld.impl[F]
      jokeAlg = Jokes.impl[F](client)

      // Combine Service Routes into an HttpApp.
      // Can also be done via a Router if you
      // want to extract a segments not checked
      // in the underlying routes.
      httpApp = (
        WeatherdemoRoutes.helloWorldRoutes[F](helloWorldAlg) <+>
          WeatherdemoRoutes.jokeRoutes[F](jokeAlg) <+>
          WeatherdemoRoutes.weatherRoutes[F](client) <+>
          WeatherdemoRoutes.otherRoute[F](client) <+>
          WeatherdemoRoutes.callbackRoute[F](client)
      ).orNotFound

      // With Middlewares in place
      finalHttpApp = Logger.httpApp(true, true)(httpApp)

      exitCode <- BlazeServerBuilder[F]
        .bindHttp(9090, "0.0.0.0")
        .withHttpApp(finalHttpApp)
        .serve
    } yield exitCode
  }.drain
}
