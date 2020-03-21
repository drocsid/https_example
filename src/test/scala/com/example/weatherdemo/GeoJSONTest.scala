package com.example.weatherdemo

import com.example.weatherdemo.GeoJSON.GeoJSON

import io.circe.Codec
import io.circe.parser.decode
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import io.circe.Codec

import io.circe.Decoder._
import io.circe.Encoder._
import io.circe._
import io.circe.generic.extras._
import io.circe.generic.extras.decoding.UnwrappedDecoder
import io.circe.generic.extras.encoding.UnwrappedEncoder
import io.circe.generic.extras.semiauto._

import scala.collection.immutable.{BitSet, IntMap}
import scala.collection.mutable
import scala.util.Try

class GeoJSONTest extends AnyFlatSpec with Matchers {

  "decode" should "decode json" in {
    val modelSchema =
      """
{
    "type": "Feature",
    "geometry": {
        "type": "GeometryCollection",
        "geometries": [
            {
                "type": "Point",
                "coordinates": [
                    -84.363234599999998,
                    33.322789800000002
                ]
            },
            {
                "type": "Polygon",
                "coordinates": [
                    [
                        [
                            -84.375653600000007,
                            33.3349385
                        ],
                        [
                            -84.377770600000005,
                            33.312410200000002
                        ],
                        [
                            -84.3508183,
                            33.310639600000002
                        ],
                        [
                            -84.348696000000004,
                            33.333167500000002
                        ],
                        [
                            -84.375653600000007,
                            33.3349385
                        ]
                    ]
                ]
            }
        ]
    }
}
"""
    implicit val geoJSONC3c: Codec[GeoJSON.GeoJSON] = {
      implicit val c1: Codec[GeoJSON.SimpleGeometry] =
        deriveConfiguredCodec[GeoJSON.SimpleGeometry]
      implicit val c2: Codec[GeoJSON.Geometry] =
        deriveConfiguredCodec[GeoJSON.Geometry]
      implicit val c3: Codec[GeoJSON.SimpleGeoJSON] =
        deriveConfiguredCodec[GeoJSON.SimpleGeoJSON]
      deriveConfiguredCodec[GeoJSON.GeoJSON]
    }
    implicit val decoded = decode[GeoJSON](modelSchema)
    println(decoded)
    decoded.isRight shouldBe (true)
  }
}
