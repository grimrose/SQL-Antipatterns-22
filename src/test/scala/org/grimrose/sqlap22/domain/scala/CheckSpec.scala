package org.grimrose.sqlap22.domain.scala

import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, fixture}
import scalikejdbc._
import scalikejdbc.scalatest.AutoRollback

@RunWith(classOf[JUnitRunner])
class CheckSpec extends fixture.FunSpec with Matchers with DBSettings with AutoRollback {

  val name = "Scala"

  override def fixture(implicit session: DBSession) = {
    withSQL {
      deleteFrom(Bug)
    }.update().apply()

    withSQL {
      delete.from(Account)
    }.update().apply()

    val c = Account.column
    val id = withSQL {
      insert.into(Account)
        .columns(c.accountName)
        .values(name)
    }.updateAndReturnGeneratedKey().apply()

    withSQL {
      val b = Bug.column
      insertInto(Bug).namedValues(
        b.dataReported -> LocalDate.now,
        b.summary -> "dummy",
        b.reportedBy -> id,
        b.assignedTo -> id,
        b.status -> "OPEN"
      )
    }.update().apply()

  }

  describe("Bug") {
    it("リズムを維持する") { implicit session =>

      val a = Account.syntax("a")
      val id = withSQL {
        select(a.accountId).from(Account as a).where.eq(a.accountName, name)
      }.map(_.long(1)).single().apply()

      val status = "OPEN"

      val (b, a1, a2, bs) = (Bug.syntax, Account.syntax("a1"), Account.syntax("a2"), BugStatus.syntax)
      val bugs: List[Bug] = withSQL {
        select
          .from(Bug as b)
          .join(Account as a1).on(b.reportedBy, a1.accountId)
          .leftJoin(Account as a2).on(b.assignedTo, a2.accountId)
          .join(BugStatus as bs).on(b.status, bs.status)
          .where
          .eq(b.assignedTo, id)
          .and
          .eq(b.status, status)
      }.map(Bug(b, a1, a2, bs)).list().apply()

      bugs should not(be(empty))
      bugs.size should be(1)
    }
  }

}
