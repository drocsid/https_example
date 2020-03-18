package com.example.weatherdemo

import com.example.weatherdemo.Util._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class UtilTest extends AnyFlatSpec {
  "isValidLatitude" should "return True / False as expected" in {
    isValidLatitude(-90.1F) shouldBe false
    isValidLatitude(-89.9F) shouldBe true
    isValidLatitude(89.9F) shouldBe true
    isValidLatitude(91.1F) shouldBe false
  }

  "isValidLongitude" should "return True / False as expected" in {
    isValidLongitude(180.1F) shouldBe false // y <= 180 && y >= -180
    isValidLongitude(179.9F) shouldBe true
    isValidLongitude(-180.1F) shouldBe false
    isValidLongitude(-179.8F) shouldBe true
  }

  "testLatitudeStringToLatitude" should "return expected" in {
    latitudeStringToLatitude("-90.1") shouldBe None
    latitudeStringToLatitude("-89.9") shouldBe Option(Latitude(-89.9F))
  }

  "testLongitudeStringToLongitude" should "return expected" in {
    longitudeStringToLongitude("181.1") shouldBe None
    longitudeStringToLongitude("179.9") shouldBe Option(Longitude(179.9F))
  }

  "incoming" should "return expected" in {
    incoming("-23, 23") shouldBe (Option(Latitude(-23), Longitude(23)))
    incoming("666, 666") shouldBe (None)
    incoming("98343") shouldBe(None)
  }
}
