package com.sharethrough.hive.udfs

import org.specs2.mutable._

class HaversinceDistanceSpec extends Specification {
  val haversinceCalculator = new HaversinceDistance

  "HaversinceDistance#evaluate" should {
    "return the haversine distance between the two sets of coordinates" in {
      haversinceCalculator.evaluate(-86.67, 36.12, -118.40, 33.94) must beCloseTo(2887.259, 0.01)
    }

  }

}
