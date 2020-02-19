package com.example.weatherdemo

import cats.syntax.functor._
import io.circe.Decoder
import io.circe.generic.auto._
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf


object GenericDerivation {
  //implicit def weatherEntityDecoder[F[_]: Sync]: EntityDecoder[F, ] = jsonOf

  implicit val decodeEvent: Decoder[Weather] =
    List[Decoder[Weather]](
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

