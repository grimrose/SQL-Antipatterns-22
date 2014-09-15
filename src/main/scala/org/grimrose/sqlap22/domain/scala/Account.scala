package org.grimrose.sqlap22.domain.scala

import scalikejdbc._

case class Account(accountId: Long, accountName: Option[String] = None)

object Account extends SQLSyntaxSupport[Account] {
  override val tableName = "Accounts"

  def apply(sp: SyntaxProvider[Account])(rs: WrappedResultSet): Account = apply(sp.resultName)(rs)

  def apply(rn: ResultName[Account])(rs: WrappedResultSet) = new Account(
    rs.long(rn.accountId),
    rs.stringOpt(rn.accountName)
  )

}
