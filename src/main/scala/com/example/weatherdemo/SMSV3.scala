package com.example.weatherdemo

  case class Message1 (
    `type`: String,
    attributes: Attributes
  )

  case class Attributes(
     to: String,
     from: String,
     body: String
  )
