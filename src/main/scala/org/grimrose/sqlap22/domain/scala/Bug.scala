package org.grimrose.sqlap22.domain.scala

import org.joda.time.LocalDate
import scalikejdbc._

case class Bug(
                bugId: Long,
                dataReported: LocalDate,
                summary: Option[String] = None,
                reportedBy: Long,
                reportedAccount: Account,
                assignedTo: Option[Long] = None,
                assignedAccount: Option[Account] = None,
                status: String,
                bugStatus: BugStatus)

object Bug extends SQLSyntaxSupport[Bug] {
  override val tableName = "Bugs"

  def apply(b: SyntaxProvider[Bug], report: SyntaxProvider[Account], assign: SyntaxProvider[Account], bs: SyntaxProvider[BugStatus])(rs: WrappedResultSet): Bug =
    apply(b.resultName, report.resultName, assign.resultName, bs.resultName)(rs)

  def apply(b: ResultName[Bug], report: ResultName[Account], assign: ResultName[Account], bs: ResultName[BugStatus])(rs: WrappedResultSet): Bug =
    new Bug(
      bugId = rs.get(b.bugId),
      dataReported = rs.get(b.dataReported),
      summary = rs.get(b.summary),
      reportedBy = rs.get(b.reportedBy),
      reportedAccount = Account(rs.long(report.accountId)),
      assignedTo = rs.get(b.assignedTo),
      assignedAccount = Account.opt(assign)(rs),
      status = rs.get(b.status),
      bugStatus = BugStatus(rs.string(bs.status))
    )

}
