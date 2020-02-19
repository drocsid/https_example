package com.example.weatherdemo

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp {
  def run(args: List[String]) =
    WeatherdemoServer.stream[IO].compile.drain.as(ExitCode.Success)
}