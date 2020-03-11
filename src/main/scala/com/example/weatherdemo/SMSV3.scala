package com.example.weatherdemo

import io.circe.syntax._
import io.circe.generic.auto._

  case class Message1 (
    `type`: String,
    attributes: Attributes
  )

  case class Attributes(
     to: String,
     from: String,
     body: String
  )
