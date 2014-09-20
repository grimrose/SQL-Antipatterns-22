package org.grimrose.sqlap22.domain.scala

import scalikejdbc._

case class BugStatus(status: String)

object BugStatus extends SQLSyntaxSupport[BugStatus] {
  override val tableName = "BugStatus"

  def apply(sp: SyntaxProvider[BugStatus])(rs: WrappedResultSet): BugStatus = apply(sp.resultName)(rs)

  def apply(rn: ResultName[BugStatus])(rs: WrappedResultSet) = new BugStatus(
    rs.string(rn.status)
  )
}
