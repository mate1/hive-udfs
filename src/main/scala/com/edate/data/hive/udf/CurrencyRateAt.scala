package com.edate.data.hive.udf

import org.apache.hadoop.hive.ql.exec.UDF
import java.sql.Timestamp
import java.sql.Date

/**
 * Hive UDF to get the currency rate from
 * the currency rate table at the given time.
 */
class CurrencyRateAt extends UDF {
  
  val curIds = Map(123 -> 0.9803407667, 826 -> 1.5208094402, 840 -> 1.0000000000)

  def evaluate(s:String, curId:Int): java.lang.Double = {
    
    try {
      val date = if (s.length() == 10) { // 10 = yyyy-mm-dd
        java.sql.Date.valueOf(s)
      } else { // assume its a Timestamp
    	new Date(Timestamp.valueOf(s).getTime())
      }
      
      getRate(date, curId)
    } catch {
      case t:Throwable => null
    }
  }
  
  protected def getRate(date:Date, curId:Int) : java.lang.Double = {
    
    // TODO: fetch this from the database
    if (curIds.contains(curId)) {
      curIds(curId)
    } else {
      null
    }
  }

}
