package com.sharethrough.hive.udfs

import org.specs2.mutable._

class GeocodeIPSpec extends Specification {
  "GeocodeIP.evaluate" should {
    val geocoder = new GeocodeIP("src/test/support/GeoLiteCity.dat")
    "return the specified field from the geocoded IP" in {
      geocoder.evaluate("50.1.107.35", "city") must_== "Santa Rosa"
      geocoder.evaluate("50.1.107.35", "lat") must_== "38.392807"
      geocoder.evaluate("50.1.107.35", "lon") must_== "-122.7507"
    }

    "return unknown if you ask for an invalid field name" in {
      geocoder.evaluate("50.1.107.35", "foobar") must_== "unknown"
    }

    "return unknown if IP is invalid" in {
      geocoder.evaluate("127.0.0.1", "lat") must_== "unknown"
    }

    "return unknown when passed null for the IP address" in {
      geocoder.evaluate(null, "lat") must_== "unknown"
    }
  }
}
