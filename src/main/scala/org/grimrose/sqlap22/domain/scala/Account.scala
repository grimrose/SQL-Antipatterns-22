package org.grimrose.sqlap22.domain.scala

import scalikejdbc._

case class Account(accountId: Long, accountName: Option[String] = None)

object Account extends SQLSyntaxSupport[Account] {
  override val tableName = "Accounts"

  def apply(a: SyntaxProvider[Account])(rs: WrappedResultSet): Account = apply(a.resultName)(rs)

  def apply(a: ResultName[Account])(rs: WrappedResultSet) = new Account(
    rs.long(a.accountId),
    rs.stringOpt(a.accountName)
  )

  def opt(a: SyntaxProvider[Account])(rs: WrappedResultSet): Option[Account] = opt(a.resultName)(rs)

  def opt(a: ResultName[Account])(rs: WrappedResultSet): Option[Account] =
    rs.longOpt(a.accountId).map(_ => Account(a)(rs))

}
