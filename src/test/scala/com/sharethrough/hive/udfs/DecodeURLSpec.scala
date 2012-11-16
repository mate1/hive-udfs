package com.sharethrough.hive.udfs

import org.specs2._

class DecodeURLSpec extends mutable.Specification {
  val decoder = new DecodeURL

  "DecodeURL#evaluate" should {
    "return an empty string if passed a null value" in {
      decoder.evaluate(null) mustEqual "Invalid"
    }

    "return invalidurl.com if there is an exception trying to decode the url" in {
      decoder.evaluate("%%2") mustEqual "http://invalid-url-decoding.com"
    }

    "return the url decoded value" in {
      decoder.evaluate("http%3A%2F%2Frandomhold.wordpress.com%2F2008") mustEqual "http://randomhold.wordpress.com/2008"
    }

    "Attempts to fix invalid trailing % values from normally invalid URIs" in {
      decoder.evaluate("http%3A%2F%2Frandomhold.wordpress.com%2F2008%2") mustEqual "http://randomhold.wordpress.com/2008"
    }
  }
}
