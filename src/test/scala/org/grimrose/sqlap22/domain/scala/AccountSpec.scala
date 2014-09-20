package org.grimrose.sqlap22.domain.scala

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, fixture}
import scalikejdbc._
import scalikejdbc.scalatest.AutoRollback

@RunWith(classOf[JUnitRunner])
class AccountSpec extends fixture.FunSpec with Matchers with DBSettings with AutoRollback {

  describe("Accounts") {
    it("id: 1, name: Scalaが見つかること") { implicit session =>
      val name = "Scala"

      val c = Account.column
      withSQL {
        insert.into(Account)
          .columns(c.accountName)
          .values(name)
      }.update().apply()

      val a = Account.syntax("a")
      val account = withSQL {
        select.from(Account as a)
          .where
          .eq(a.accountName, name)
      }.map(Account(a)).single().apply()

      account should not(be(empty))
      account.flatMap(_.accountName) should be(Some(name))
    }
  }

}
