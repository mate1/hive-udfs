package com.edate.data.hive.udf

import org.specs2.mutable._

import com.edate.data.hive.udf.GeocodeIP;

class GeocodeIPSpec extends Specification {
  "GeocodeIP.evaluate" should {
    val pathToGeoIpDatabase = "src/test/support/GeoLiteCity.dat"
    val geocoder = new GeocodeIP
    "return the specified field from the geocoded IP" in {
      geocoder.evaluate("50.1.107.35", "city", pathToGeoIpDatabase) must_== "Santa Rosa"
      geocoder.evaluate("50.1.107.35", "lat", pathToGeoIpDatabase) must_== "38.392807"
      geocoder.evaluate("50.1.107.35", "lon", pathToGeoIpDatabase) must_== "-122.7507"
      geocoder.evaluate("50.1.107.35", "dma_code", pathToGeoIpDatabase) must_== "807"
    }

    "return unknown if you ask for an invalid field name" in {
      geocoder.evaluate("50.1.107.35", "foobar", pathToGeoIpDatabase) must_== "unknown"
    }

    "return unknown if IP is invalid" in {
      geocoder.evaluate("127.0.0.1", "lat", pathToGeoIpDatabase) must_== "unknown"
    }

    "return unknown if IP is invalid string" in {
      geocoder.evaluate("hello world", "lat", pathToGeoIpDatabase) must_== "unknown"
    }

    "return unknown when passed null for the IP address" in {
      geocoder.evaluate(null, "lat", pathToGeoIpDatabase) must_== "unknown"
    }
  }
}
