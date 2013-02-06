package com.sharethrough.hive.udfs

import org.specs2.mutable._

class GeocodeIPSpec extends Specification {
  "GeocodeIP.evaluate" should {
    val geocoder = new GeocodeIP("src/test/support/GeoLiteCity.dat")
    "return a hashmap containing the geocoded data when given a valid ip" in {
      val expectedOutput = Map(
        "city" -> "Santa Rosa",
        "country" -> "United States",
        "lat" -> 38.392807F,
        "lon" -> -122.7507F
      )
      geocoder.evaluate("50.1.107.35") must_== expectedOutput
    }

    "return an empty hashmap when given an invalid IP address" in {
      val expectedOutput = Map.empty[String, Any]
      geocoder.evaluate("127.0.0.1") must_== expectedOutput
    }

    "return an emtpy hashmap when passed null for the IP address" in {
      val expectedOutput = Map.empty[String, Any]
      geocoder.evaluate(null) must_== expectedOutput
    }
  }
}
