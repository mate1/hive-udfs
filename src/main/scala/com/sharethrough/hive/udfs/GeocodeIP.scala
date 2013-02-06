package com.sharethrough.hive.udfs

import org.apache.hadoop.hive.ql.exec.UDF
import com.maxmind.geoip._

/**
 * Hive UDF to take an IP address and return the given
 * field from the geocoded data
 *
 * Example:
 *  geocode_ip('127.0.0.1', 'city')
 */
class GeocodeIP(val pathToIPDatabase: String) extends UDF {

  var geocoder = new LookupService(
    pathToIPDatabase,
    LookupService.GEOIP_MEMORY_CACHE);

  def this() = {
    this("GeoLiteCity.dat")
  }

  def evaluate(ipAddress: String, fieldName: String): String = {
    val locationData = geocoder.getLocation(ipAddress)
    if (locationData != null) {
      Map(
        "city" -> locationData.city,
        "country" -> locationData.countryName,
        "lat" -> locationData.latitude.toString,
        "lon" -> locationData.longitude.toString
      ).getOrElse(fieldName, "unknown")
    } else {
      "unknown"
    }
  }
}

