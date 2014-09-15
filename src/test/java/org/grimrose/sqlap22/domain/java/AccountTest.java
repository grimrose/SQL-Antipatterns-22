package org.grimrose.sqlap22.domain.java;

import com.google.common.base.Optional;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.Description;

import java.sql.*;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class AccountTest {

    private static final String URL = "jdbc:h2:file:./db/default";
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

    @Before
    public void setUp() throws Exception {
        // Setup
        String sql = "delete from Accounts";
        new QueryRunner().update(connection, sql);
    }


    @Test
    public void _id_1_かつ_name_Javaのアカウントが見つかること() throws Exception {
        // Setup
        int id = 1;
        String name = "Java";
        insert(id, name);

        // Exercise
        Optional<Account> actual = findByIdAndName(id, name);
        // Verify
        assertThat(actual.isPresent(), is(true));
    }

    private int insert(long id, String name) throws SQLException {
        String sql = "insert into Accounts(account_id, account_name) values (?, ?)";
        return new QueryRunner().update(connection, sql, id, name);
    }

    private Optional<Account> findByIdAndName(long id, String name) throws SQLException {
        String sql = "select * from Accounts where account_id = ? and account_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object param : Arrays.asList(id, name)) {
                ps.setObject(index++, param);
            }
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
