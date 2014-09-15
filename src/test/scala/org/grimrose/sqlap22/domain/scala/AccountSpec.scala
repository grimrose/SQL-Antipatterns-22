package org.grimrose.sqlap22.domain.scala

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, fixture}
import scalikejdbc._
import scalikejdbc.scalatest.AutoRollback

@RunWith(classOf[JUnitRunner])
class AccountSpec extends fixture.FunSpec with Matchers with DBSettings with AutoRollback {

  override def fixture(implicit session: DBSession) = {
    withSQL {
      delete.from(Account)
    }.update().apply()
  }

  describe("Accounts") {
    it("id: 1, name: Scalaが見つかること") { implicit session =>
      val id = 1
      val name = "Scala"

      val c = Account.column
      withSQL {
        insert.into(Account)
          .columns(c.accountId, c.accountName)
          .values(id, name)
      }.update().apply()

      val a = Account.syntax("a")
      val account = withSQL {
        select.from(Account as a)
          .where.eq(a.accountId, id)
          .and
          .eq(a.accountName, name)
      }.map(Account(a)).single().apply()

      account should not(be(empty))
      account.map(_.accountId) should be(Some(id))
      account.flatMap(_.accountName) should be(Some(name))
    }
  }

}
