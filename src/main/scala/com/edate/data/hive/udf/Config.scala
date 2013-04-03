package com.edate.data.hive.udf

import com.typesafe.config.ConfigFactory

object Config {
  protected val config = ConfigFactory.load()
  
  def apply() = config
}