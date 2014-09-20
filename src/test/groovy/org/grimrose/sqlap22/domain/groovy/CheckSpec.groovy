package org.grimrose.sqlap22.domain.groovy

import groovy.sql.Sql
import groovy.util.logging.Slf4j
import spock.lang.Shared
import spock.lang.Specification

import java.sql.SQLException

@Slf4j
class CheckSpec extends Specification {

    static final String URL = "jdbc:log4jdbc:h2:file:./db/default"
    static final String USER = "sa"
    static final String PASSWORD = ""

    def name = 'Groovy'

    @Shared
    Sql db

    def setupSpec() {
        db = Sql.newInstance(URL, USER, PASSWORD)
    }

    def setup() {
        db.execute("delete from Bugs")
        db.execute("delete from Accounts")

        def accountIds = db.executeInsert("""
insert into Accounts (account_name) values (?)
""",
                [name], ['account_id'])

        def id = accountIds[0][0]

        def bug = [
                dataReported: new Date(),
                summary     : "dummy",
                reportedBy  : id,
                assigedTo   : id,
                status      : 'OPEN'
        ]

        db.execute("""
insert into Bugs (data_reported, summary, reported_by, assigned_to, status)
values            (?, ?, ?, ?, ?)
""",
                bug.dataReported, bug.summary, bug.reportedBy, bug.assigedTo, bug.status)
    }

    def cleanupSpec() {
        db.execute("delete from Bugs")
        db.execute("delete from Accounts")
    }

    def "リズムを維持する"() {
        expect:
        def rows = []
        Sql.withInstance(URL, USER, PASSWORD) { Sql session ->
            try {
                def account = session.firstRow("""
select account_id as id, account_name as name from Accounts
where account_name = ?
""",
                        name)
                def id = account.id
                def status = 'OPEN'
                rows = session.rows("""
SELECT bug_id, summary, data_reported FROM Bugs
WHERE assigned_to = ? AND status = ?
""",
                        id, status)
            } catch (SQLException e) {
                log.error(e.message, e)
            }
        }
        assert rows
    }

}
