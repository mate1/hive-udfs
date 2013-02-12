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
    if(!validIpAddress(ipAddress)) {
      return "unknown"
    }
    val locationData = geocoder.getLocation(ipAddress)
    if (locationData != null) {
      Map(
        "city" -> locationData.city,
        "country" -> locationData.countryName,
        "dma_code" -> locationData.dma_code.toString,
        "lat" -> locationData.latitude.toString,
        "lon" -> locationData.longitude.toString
      ).getOrElse(fieldName, "unknown")
    } else {
      "unknown"
    }
  }

  private def validIpAddress(ipAddress: String): Boolean = {
    if(ipAddress == null) return false
    ipAddress.matches(GeocodeIP.ipAddressPattern)
  }
}

object GeocodeIP {
  val ipAddressPattern =
    "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$".r
}


