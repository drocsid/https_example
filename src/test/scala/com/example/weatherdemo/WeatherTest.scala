package com.example.weatherdemo

import io.circe.parser.decode
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class WeatherTest extends AnyFlatSpec with Matchers {

  "decode" should "decode json" in {
    val modelSchema =
      """{
    "@context": [
        "https://raw.githubusercontent.com/geojson/geojson-ld/master/contexts/geojson-base.jsonld",
        {
            "wx": "https://api.weather.gov/ontology#",
            "s": "https://schema.org/",
            "geo": "http://www.opengis.net/ont/geosparql#",
            "unit": "http://codes.wmo.int/common/unit/",
            "@vocab": "https://api.weather.gov/ontology#",
            "geometry": {
                "@id": "s:GeoCoordinates",
                "@type": "geo:wktLiteral"
            },
            "city": "s:addressLocality",
            "state": "s:addressRegion",
            "distance": {
                "@id": "s:Distance",
                "@type": "s:QuantitativeValue"
            },
            "bearing": {
                "@type": "s:QuantitativeValue"
            },
            "value": {
                "@id": "s:value"
            },
            "unitCode": {
                "@id": "s:unitCode",
                "@type": "@id"
            },
            "forecastOffice": {
                "@type": "@id"
            },
            "forecastGridData": {
                "@type": "@id"
            },
            "publicZone": {
                "@type": "@id"
            },
            "county": {
                "@type": "@id"
            }
        }
    ],
    "id": "https://api.weather.gov/points/39,-97",
    "type": "Feature",
    "geometry": {
        "type": "Point",
        "coordinates": [
            -97,
            39
        ]
    },
    "properties": {
        "@id": "https://api.weather.gov/points/39,-97",
        "@type": "wx:Point",
        "cwa": "TOP",
        "forecastOffice": "https://api.weather.gov/offices/TOP",
        "gridX": 34,
        "gridY": 46,
        "forecast": "https://api.weather.gov/gridpoints/TOP/34,46/forecast",
        "forecastHourly": "https://api.weather.gov/gridpoints/TOP/34,46/forecast/hourly",
        "forecastGridData": "https://api.weather.gov/gridpoints/TOP/34,46",
        "observationStations": "https://api.weather.gov/gridpoints/TOP/34,46/stations",
        "relativeLocation": {
            "type": "Feature",
            "geometry": {
                "type": "Point",
                "coordinates": [
                    -97.023261000000005,
                    38.973858999999997
                ]
            },
            "properties": {
                "city": "Chapman",
                "state": "KS",
                "distance": {
                    "value": 3534.2854365990561,
                    "unitCode": "unit:m"
                },
                "bearing": {
                    "value": 34,
                    "unitCode": "unit:degrees_true"
                }
            }
        },
        "forecastZone": "https://api.weather.gov/zones/forecast/KSZ035",
        "county": "https://api.weather.gov/zones/county/KSC041",
        "fireWeatherZone": "https://api.weather.gov/zones/fire/KSZ035",
        "timeZone": "America/Chicago",
        "radarStation": "KTWX"
    }
}"""
    implicit val decoded = decode[Weather](modelSchema)
    println(decoded)
    decoded.isRight shouldBe (true)
  }
}

