package com.example.weatherdemo

import io.circe.parser.decode
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class WeatherForecastTest extends AnyFlatSpec with Matchers {

  "decode" should "decode json" in {
    val modelSchema =
      """
{
    "@context": [
        "https://raw.githubusercontent.com/geojson/geojson-ld/master/contexts/geojson-base.jsonld",
        {
            "wx": "https://api.weather.gov/ontology#",
            "geo": "http://www.opengis.net/ont/geosparql#",
            "unit": "http://codes.wmo.int/common/unit/",
            "@vocab": "https://api.weather.gov/ontology#"
        }
    ],
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
    },
    "properties": {
        "updated": "2020-03-20T08:12:07+00:00",
        "units": "us",
        "forecastGenerator": "BaselineForecastGenerator",
        "generatedAt": "2020-03-20T09:07:55+00:00",
        "updateTime": "2020-03-20T08:12:07+00:00",
        "validTimes": "2020-03-20T02:00:00+00:00/P8DT6H",
        "elevation": {
            "value": 255.11760000000001,
            "unitCode": "unit:m"
        },
        "periods": [
            {
                "number": 1,
                "name": "Overnight",
                "startTime": "2020-03-20T05:00:00-04:00",
                "endTime": "2020-03-20T06:00:00-04:00",
                "isDaytime": false,
                "temperature": 61,
                "temperatureUnit": "F",
                "temperatureTrend": null,
                "windSpeed": "5 mph",
                "windDirection": "SW",
                "icon": "https://api.weather.gov/icons/land/night/sct?size=medium",
                "shortForecast": "Partly Cloudy",
                "detailedForecast": "Partly cloudy, with a low around 61. Southwest wind around 5 mph."
            },
            {
                "number": 2,
                "name": "Friday",
                "startTime": "2020-03-20T06:00:00-04:00",
                "endTime": "2020-03-20T18:00:00-04:00",
                "isDaytime": true,
                "temperature": 81,
                "temperatureUnit": "F",
                "temperatureTrend": null,
                "windSpeed": "5 to 10 mph",
                "windDirection": "SW",
                "icon": "https://api.weather.gov/icons/land/day/bkn/rain_showers,40?size=medium",
                "shortForecast": "Partly Sunny then Chance Rain Showers",
                "detailedForecast": "A chance of rain showers after noon. Partly sunny, with a high near 81. Southwest wind 5 to 10 mph, with gusts as high as 20 mph. Chance of precipitation is 40%."
            },
            {
                "number": 3,
                "name": "Friday Night",
                "startTime": "2020-03-20T18:00:00-04:00",
                "endTime": "2020-03-21T06:00:00-04:00",
                "isDaytime": false,
                "temperature": 59,
                "temperatureUnit": "F",
                "temperatureTrend": null,
                "windSpeed": "5 to 10 mph",
                "windDirection": "W",
                "icon": "https://api.weather.gov/icons/land/night/tsra,70/tsra,60?size=medium",
                "shortForecast": "Showers And Thunderstorms Likely",
                "detailedForecast": "Showers and thunderstorms likely. Mostly cloudy, with a low around 59. West wind 5 to 10 mph. Chance of precipitation is 70%. New rainfall amounts between a tenth and quarter of an inch possible."
            },
            {
                "number": 4,
                "name": "Saturday",
                "startTime": "2020-03-21T06:00:00-04:00",
                "endTime": "2020-03-21T18:00:00-04:00",
                "isDaytime": true,
                "temperature": 75,
                "temperatureUnit": "F",
                "temperatureTrend": null,
                "windSpeed": "5 to 10 mph",
                "windDirection": "NW",
                "icon": "https://api.weather.gov/icons/land/day/rain_showers,30?size=medium",
                "shortForecast": "Chance Rain Showers",
                "detailedForecast": "A chance of rain showers. Mostly cloudy, with a high near 75. Northwest wind 5 to 10 mph. Chance of precipitation is 30%."
            },
            {
                "number": 5,
                "name": "Saturday Night",
                "startTime": "2020-03-21T18:00:00-04:00",
                "endTime": "2020-03-22T06:00:00-04:00",
                "isDaytime": false,
                "temperature": 53,
                "temperatureUnit": "F",
                "temperatureTrend": null,
                "windSpeed": "0 to 10 mph",
                "windDirection": "NW",
                "icon": "https://api.weather.gov/icons/land/night/rain_showers,20/bkn?size=medium",
                "shortForecast": "Slight Chance Rain Showers then Mostly Cloudy",
                "detailedForecast": "A slight chance of rain showers before 8pm. Mostly cloudy, with a low around 53. Northwest wind 0 to 10 mph. Chance of precipitation is 20%."
            },
            {
                "number": 6,
                "name": "Sunday",
                "startTime": "2020-03-22T06:00:00-04:00",
                "endTime": "2020-03-22T18:00:00-04:00",
                "isDaytime": true,
                "temperature": 66,
                "temperatureUnit": "F",
                "temperatureTrend": null,
                "windSpeed": "0 to 5 mph",
                "windDirection": "NE",
                "icon": "https://api.weather.gov/icons/land/day/rain_showers,50/rain_showers,60?size=medium",
                "shortForecast": "Rain Showers Likely",
                "detailedForecast": "Rain showers likely after 8am. Mostly cloudy, with a high near 66. Chance of precipitation is 60%."
            },
            {
                "number": 7,
                "name": "Sunday Night",
                "startTime": "2020-03-22T18:00:00-04:00",
                "endTime": "2020-03-23T06:00:00-04:00",
                "isDaytime": false,
                "temperature": 56,
                "temperatureUnit": "F",
                "temperatureTrend": null,
                "windSpeed": "0 to 5 mph",
                "windDirection": "SE",
                "icon": "https://api.weather.gov/icons/land/night/tsra,70?size=medium",
                "shortForecast": "Showers And Thunderstorms Likely",
                "detailedForecast": "Rain showers likely before 8pm, then showers and thunderstorms likely. Cloudy, with a low around 56. Chance of precipitation is 70%."
            },
            {
                "number": 8,
                "name": "Monday",
                "startTime": "2020-03-23T06:00:00-04:00",
                "endTime": "2020-03-23T18:00:00-04:00",
                "isDaytime": true,
                "temperature": 69,
                "temperatureUnit": "F",
                "temperatureTrend": null,
                "windSpeed": "0 to 5 mph",
                "windDirection": "SW",
                "icon": "https://api.weather.gov/icons/land/day/tsra,70/tsra,60?size=medium",
                "shortForecast": "Showers And Thunderstorms Likely",
                "detailedForecast": "Showers and thunderstorms likely. Cloudy, with a high near 69. Chance of precipitation is 70%."
            },
            {
                "number": 9,
                "name": "Monday Night",
                "startTime": "2020-03-23T18:00:00-04:00",
                "endTime": "2020-03-24T06:00:00-04:00",
                "isDaytime": false,
                "temperature": 54,
                "temperatureUnit": "F",
                "temperatureTrend": null,
                "windSpeed": "0 to 5 mph",
                "windDirection": "N",
                "icon": "https://api.weather.gov/icons/land/night/tsra,60/tsra,30?size=medium",
                "shortForecast": "Showers And Thunderstorms Likely",
                "detailedForecast": "Showers and thunderstorms likely. Mostly cloudy, with a low around 54. Chance of precipitation is 60%."
            },
            {
                "number": 10,
                "name": "Tuesday",
                "startTime": "2020-03-24T06:00:00-04:00",
                "endTime": "2020-03-24T18:00:00-04:00",
                "isDaytime": true,
                "temperature": 73,
                "temperatureUnit": "F",
                "temperatureTrend": null,
                "windSpeed": "0 to 5 mph",
                "windDirection": "SE",
                "icon": "https://api.weather.gov/icons/land/day/tsra_sct,30?size=medium",
                "shortForecast": "Chance Showers And Thunderstorms",
                "detailedForecast": "A chance of showers and thunderstorms. Mostly cloudy, with a high near 73. Chance of precipitation is 30%."
            },
            {
                "number": 11,
                "name": "Tuesday Night",
                "startTime": "2020-03-24T18:00:00-04:00",
                "endTime": "2020-03-25T06:00:00-04:00",
                "isDaytime": false,
                "temperature": 61,
                "temperatureUnit": "F",
                "temperatureTrend": null,
                "windSpeed": "5 mph",
                "windDirection": "SW",
                "icon": "https://api.weather.gov/icons/land/night/tsra,60?size=medium",
                "shortForecast": "Showers And Thunderstorms Likely",
                "detailedForecast": "Showers and thunderstorms likely. Mostly cloudy, with a low around 61. Chance of precipitation is 60%."
            },
            {
                "number": 12,
                "name": "Wednesday",
                "startTime": "2020-03-25T06:00:00-04:00",
                "endTime": "2020-03-25T18:00:00-04:00",
                "isDaytime": true,
                "temperature": 76,
                "temperatureUnit": "F",
                "temperatureTrend": null,
                "windSpeed": "5 to 10 mph",
                "windDirection": "W",
                "icon": "https://api.weather.gov/icons/land/day/tsra_sct,60/tsra_sct,40?size=medium",
                "shortForecast": "Showers And Thunderstorms Likely",
                "detailedForecast": "Showers and thunderstorms likely. Partly sunny, with a high near 76. Chance of precipitation is 60%."
            },
            {
                "number": 13,
                "name": "Wednesday Night",
                "startTime": "2020-03-25T18:00:00-04:00",
                "endTime": "2020-03-26T06:00:00-04:00",
                "isDaytime": false,
                "temperature": 57,
                "temperatureUnit": "F",
                "temperatureTrend": null,
                "windSpeed": "0 to 5 mph",
                "windDirection": "W",
                "icon": "https://api.weather.gov/icons/land/night/tsra_hi,40/tsra_hi,20?size=medium",
                "shortForecast": "Chance Showers And Thunderstorms",
                "detailedForecast": "A chance of showers and thunderstorms. Partly cloudy, with a low around 57. Chance of precipitation is 40%."
            },
            {
                "number": 14,
                "name": "Thursday",
                "startTime": "2020-03-26T06:00:00-04:00",
                "endTime": "2020-03-26T18:00:00-04:00",
                "isDaytime": true,
                "temperature": 79,
                "temperatureUnit": "F",
                "temperatureTrend": null,
                "windSpeed": "0 to 5 mph",
                "windDirection": "SW",
                "icon": "https://api.weather.gov/icons/land/day/rain_showers,20?size=medium",
                "shortForecast": "Slight Chance Rain Showers",
                "detailedForecast": "A slight chance of rain showers. Mostly sunny, with a high near 79. Chance of precipitation is 20%."
            }
        ]
    }
}
"""

    implicit val decoded = decode[WeatherForecast](modelSchema)
    println(decoded)
    decoded.isRight shouldBe (true)
  }
}

