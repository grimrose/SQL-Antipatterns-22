package org.grimrose.sqlap22.domain.scala

import scalikejdbc.config.DBs
import scalikejdbc.{ConnectionPool, LoanPattern}


trait DBSettings extends LoanPattern {

  try {
    using(ConnectionPool.borrow()) { conn => }
  } catch {
    case e: Exception =>
      DBs.setup()
  }
}
