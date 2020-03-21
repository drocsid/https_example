package com.example.weatherdemo
import cats.implicits._

object Util {
  case class Latitude(coordinate: Float)
  case class Longitude(coordinate: Float)

  val isValidLatitude: Float => Boolean = x => x <= 90 && x >= -90
  val isValidLongitude: Float => Boolean = y => y <= 180 && y >= -180

  val latitudeStringToLatitude: String => Option[Latitude] =
    _.toFloatOption.filter(isValidLatitude).map(Latitude)
  val longitudeStringToLongitude: String => Option[Longitude] =
    _.toFloatOption.filter(isValidLongitude).map(Longitude)

  def incoming(incoming: String): Option[(Latitude, Longitude)] = {
    val regex =
      raw"\s*(-?[0-9]{1,3}(?:\.[0-9]{1,10})?)\s*(?>\s|,)\s*(-?[0-9]{1,3}(?:\.[0-9]{1,10})?)\s*".r
    val zip = raw"\s*([0-9]{5})".r

    incoming match {
      case regex(lat, lon) =>
        (latitudeStringToLatitude(lat), longitudeStringToLongitude(lon)).tupled //.toRight()
      case zip(code) => None
      case _         => None
    }
  }

}
