package com.sharethrough.hive.udfs

import org.apache.hadoop.hive.ql.exec.UDF
import math._

/**
 * Hive UDF to compute the Haversine Distance between
 * two points on the globe.
 */
class HaversinceDistance extends UDF {
  val earthRadiusInKm = 6372.8

  def evaluate(lon1: Double, lat1: Double, lon2: Double, lat2: Double): Double = {
    val dLat=(lat2 - lat1).toRadians
    val dLon=(lon2 - lon1).toRadians

    val a = pow(sin(dLat/2),2) + pow(sin(dLon/2),2) * cos(lat1.toRadians) * cos(lat2.toRadians)
    val c = 2 * asin(sqrt(a))
    earthRadiusInKm * c
  }

}
