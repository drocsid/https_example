package com.example.weatherdemo

import com.example.weatherdemo.Util._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class UtilTest extends AnyFlatSpec {
  "isValidLatitude" should "return True / False as expected" in {
    isValidLatitude(-90.1f) shouldBe false
    isValidLatitude(-89.9f) shouldBe true
    isValidLatitude(89.9f) shouldBe true
    isValidLatitude(91.1f) shouldBe false
  }

  "isValidLongitude" should "return True / False as expected" in {
    isValidLongitude(180.1f) shouldBe false // y <= 180 && y >= -180
    isValidLongitude(179.9f) shouldBe true
    isValidLongitude(-180.1f) shouldBe false
    isValidLongitude(-179.8f) shouldBe true
  }

  "testLatitudeStringToLatitude" should "return expected" in {
    latitudeStringToLatitude("-90.1") shouldBe None
    latitudeStringToLatitude("-89.9") shouldBe Option(Latitude(-89.9f))
  }

  "testLongitudeStringToLongitude" should "return expected" in {
    longitudeStringToLongitude("181.1") shouldBe None
    longitudeStringToLongitude("179.9") shouldBe Option(Longitude(179.9f))
  }

  "incoming" should "return expected" in {
    incoming("-23, 23") shouldBe (Option(Latitude(-23), Longitude(23)))
    incoming("666, 666") shouldBe (None)
    incoming("98343") shouldBe (None)
  }
}
