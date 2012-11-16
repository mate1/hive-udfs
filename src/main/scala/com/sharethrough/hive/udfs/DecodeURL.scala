package com.sharethrough.hive.udfs

import java.net.URLDecoder
import org.apache.hadoop.hive.ql.exec.UDF

class DecodeURL extends UDF {
  def evaluate(rawValue: String): String = {
    try {
      URLDecoder.decode(rawValue, "UTF-8")
    } catch {
      case e:java.lang.IllegalArgumentException => {
        decodeInvalidFormatting(rawValue)
      }
      case e:java.lang.NullPointerException => "Invalid"
    }
  }

  private def decodeInvalidFormatting(rawValue: String) = {
    val rawWithStrippedTrailing = rawValue.substring(0, rawValue.lastIndexOf("%"))
    try {
      URLDecoder.decode(rawWithStrippedTrailing, "UTF-8")
    } catch {
      case _ => "Invalid"
    }
  }
}
