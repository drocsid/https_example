package com.example.weatherdemo

import com.example.weatherdemo.GeoJSON.GeoJSON

import io.circe.parser.decode
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

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
    implicit val decoded = decode[GeoJSON](modelSchema)
    println(decoded)
    decoded.isRight shouldBe (true)
  }
}
