package com.edate.data.hive.udf

import scala.collection.JavaConversions._
import org.apache.hadoop.hive.ql.exec.UDF
import java.sql.Timestamp
import java.sql.Date
import com.jolbox.bonecp.BoneCPConfig
import com.jolbox.bonecp.BoneCP
import java.util.concurrent.ConcurrentSkipListMap

/**
 * Hive UDF to get the currency rate from
 * the currency rate table (in MySQL) at the given time.
 * 
 * Caches the entire table to avoid hitting the network.
 */
class CurrencyRateAt extends UDF {
  
  def evaluate(s:String, curId:Int): java.lang.Double = {
    
    try {
      val date = if (s.length() == 10) { // 10 = yyyy-mm-dd
        java.sql.Date.valueOf(s)
      } else { // assume its a Timestamp
    	new Date(Timestamp.valueOf(s).getTime())
      }
      
      CurrencyRateAt.getRate(date, curId)
    } catch {
      case t:Throwable => null
    }
  }  
}

object CurrencyRateAt {
    
  Class.forName("com.mysql.jdbc.Driver")
  
  protected val config = new BoneCPConfig()
  config.setJdbcUrl(Config().getString("hive-udfs.db.url"))
  config.setUsername(Config().getString("hive-udfs.db.username"))
  config.setPassword(Config().getString("hive-udfs.db.password"))
  config.setMinConnectionsPerPartition(5)
  config.setMaxConnectionsPerPartition(10)
  config.setPartitionCount(1)

  val rate = Config().getString("hive-udf.db.field.rate") // Double
  val date = Config().getString("hive-udf.db.field.rate") // Date
  val currencyId = Config().getString("hive-udf.db.field.rate") // Int
  val table = Config().getString("hive-udf.db.table")
  
  val q = "SELECT %s, %s, %s FROM %s ORDER BY %s".format(rate, date, currencyId, table, date)
  val pool = new BoneCP(config)
  
  val rates = new ConcurrentSkipListMap[Long, java.lang.Double]()
  
  loadRates()
  
  def loadRates() {
    val conn = pool.getConnection()
    val st = conn.createStatement()
    val rs = st.executeQuery(q)
    
    while (rs.next()) {
      try {
        // "currencyId" concat'ed with unix_time(date).toString
        val key = makeKey(Date.valueOf(rs.getString(date)).getTime(), rs.getInt(currencyId))
        rates += (key -> new java.lang.Double(rs.getDouble(rate)))
      } catch {
        case t:Throwable =>
      }
    }
    
    rs.close()
    st.close()
    conn.close()
  }
  
  def makeKey(date:Long, curId:Int) = (curId.toString + date.toString).toLong
  
  def getRate(date:Date, curId:Int) : java.lang.Double = {    
    rates.get(rates.floorKey(makeKey(date.getTime(), curId)))
  }
}
