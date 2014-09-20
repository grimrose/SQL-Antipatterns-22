package org.grimrose.sqlap22.domain.java;

import com.google.common.base.Optional;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.Description;

import java.sql.*;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;


public class AccountTest {

    private static final String URL = "jdbc:log4jdbc:h2:file:./db/default";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static Connection connection;

    @ClassRule
    public static ExternalResource rule = new ExternalResource() {
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
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
        }

        @Override
        protected void after() {
            DbUtils.rollbackAndCloseQuietly(connection);
        }
    };

    @Test
    public void _insertしたJavaのアカウントが見つかること() throws Exception {
        // Setup
        String name = "Java";
        Integer id = insert(name);

        // Exercise
        Optional<Account> actual = findByIdAndName(id, name);

        // Verify
        assertThat(actual.isPresent(), is(true));
        assertThat(actual.or(new Account()).getAccountId(), is(not(0L)));
        assertThat(actual.or(new Account()).getAccountName().or(""), is(name));
    }

    private Integer insert(String name) throws SQLException {
        String sql = "insert into Accounts(account_name) values (?)";
        return new QueryRunner().insert(connection, sql, new ScalarHandler<Integer>(), name);
    }

    private Optional<Account> findByIdAndName(long id, String name) throws SQLException {
        String sql = "select account_id, account_name from Accounts where account_id = ? and account_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.setString(2, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    long accountId = rs.getLong("account_id");
                    Optional<String> accountName = Optional.fromNullable(rs.getString("account_name"));

                    Account account = new Account();
                    account.setAccountId(accountId);
                    account.setAccountName(accountName);
                    return Optional.of(account);
                }
                return Optional.absent();
            }
        }
    }

}
