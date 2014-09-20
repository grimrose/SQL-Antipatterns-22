package org.grimrose.sqlap22.domain.java;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.rules.ExternalResource;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class CheckTestHelper extends ExternalResource {

    private static Logger logger = LoggerFactory.getLogger(CheckTestHelper.class);

    private String url;
    private String user;
    private String password;
    private Connection connection;

    public CheckTestHelper(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public org.junit.runners.model.Statement apply(final org.junit.runners.model.Statement base, Description description) {
        return new org.junit.runners.model.Statement() {
            @Override
            public void evaluate() throws Throwable {
                before();
                try {
                    base.evaluate();
                } finally {
                    after();
                }
            }
        };
    }

    @Override
    protected void before() throws Throwable {
        DbUtils.loadDriver("org.h2.Driver");
        connection = DriverManager.getConnection(url, user, password);
        new QueryRunner().update(connection, "delete from Bugs");
        new QueryRunner().update(connection, "delete from Accounts");
    }

    @Override
    protected void after() {
        try {
            new QueryRunner().update(connection, "delete from Bugs");
            new QueryRunner().update(connection, "delete from Accounts");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        DbUtils.rollbackAndCloseQuietly(connection);
    }

    public Integer insertAccount(String accountName) throws SQLException {
        String sql = "insert into Accounts(account_name) values (?)";
        return new QueryRunner().insert(connection, sql, new ScalarHandler<Integer>(), accountName);
    }

    public void insertBug(String summary, long reportedBy, long assignedTo, String status) throws SQLException {
        String sql = "insert into " +
                "Bugs (data_reported, summary, reported_by, assigned_to, status) " +
                "values (?, ?, ?, ?, ?)";
        new QueryRunner().update(connection, sql, new Date(), summary, reportedBy, assignedTo, status);
    }

}
