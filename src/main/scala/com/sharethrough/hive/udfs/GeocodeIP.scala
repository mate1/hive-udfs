package com.sharethrough.hive.udfs

import org.apache.hadoop.hive.ql.exec.UDF
import com.maxmind.geoip._

/**
 * Hive UDF to take an IP address and return a hashmap
 * of relevant geocode data.
 */
class GeocodeIP(val pathToIPDatabase: String) extends UDF {

  val geocoder = new LookupService(
    pathToIPDatabase,
    LookupService.GEOIP_MEMORY_CACHE);

  def evaluate(ipAddress: String): Map[String, Any] = {
    val locationData = geocoder.getLocation(ipAddress)
    if (locationData != null) {
      Map(
        "city" -> locationData.city,
        "country" -> locationData.countryName,
        "lat" -> locationData.latitude,
        "lon" -> locationData.longitude)
    } else {
      Map.empty[String, Any]
    }
  }
}

