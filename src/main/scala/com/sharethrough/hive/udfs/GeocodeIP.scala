package com.sharethrough.hive.udfs

import org.apache.hadoop.hive.ql.exec.UDF
import com.maxmind.geoip._

/**
 * Hive UDF to take an IP address and return the given
 * field from the geocoded data
 *
 * Example:
 *  geocode_ip('127.0.0.1', 'city', 'path to GeoLite DB in distributed cache')
 */
class GeocodeIP extends UDF {

  var _geocoder: Option[LookupService] = None

  def evaluate(ipAddress: String, fieldName: String, pathToIpDatabase: String): String = {
    if(!validIpAddress(ipAddress)) {
      return "unknown"
    }
    val locationData = geocoder(pathToIpDatabase).getLocation(ipAddress)
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

  private def geocoder(pathToIpDatabase: String): LookupService = {
    _geocoder match {
      case Some(lookupService) => lookupService
      case None => {
        val lookupService = new LookupService(
          pathToIpDatabase,
          LookupService.GEOIP_MEMORY_CACHE)
        _geocoder = Some(lookupService)
        lookupService
      }
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


