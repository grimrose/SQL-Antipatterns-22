package org.grimrose.sqlap22.domain.java;


import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.grimrose.sqlap22.domain.java.SQLErrorCodeMatcher.hasErrorCode;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

@RunWith(Enclosed.class)
public class SeeNoEvilTest {

    private static Logger logger = LoggerFactory.getLogger(SeeNoEvilTest.class);

    public static class _22_2_1 {

        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        private static final String user = "dbuser";
        private static final String pass = "dbpassword";
        private static final String database = "test";
        private static final String server = "db.example.com";

        @Test
        public void _診断せずに判断する() throws Exception {
            // Setup
            String url = "jdbc:mysql://" + server + "/" + database;
            expectedException.expect(SQLException.class);
            expectedException.expectMessage(is("No suitable driver found for jdbc:mysql://db.example.com/test"));

            // Exercise
            Connection connection = DriverManager.getConnection(url, user, pass);

            String sql = "SELECT bug_id, summary, date_reported FROM Bugs" +
                    " WHERE assigned_to = ? AND status = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, 1);
            ps.setString(2, "OPEN");

            ResultSet rs = ps.executeQuery();
            List<Map<String, Object>> bugs = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> bug = new HashMap<>();
                bug.put("bug_id", rs.getLong("bug_id"));
                bug.put("summary", rs.getString("summary"));
                bug.put("date_reported", rs.getDate("date_reported"));
                bugs.add(bug);
            }

            // Verify
            assertThat(bugs.isEmpty(), is(not(true)));
        }

    }


    public static class _22_2_2 {

        private static final String URL = "jdbc:log4jdbc:h2:file:./db/default";
        private static final String USER = "sa";
        private static final String PASSWORD = "";

        @ClassRule
        public static TestHelper helper = new TestHelper(URL, USER, PASSWORD);

        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @Test
        public void _見逃しがちなコード() throws Exception {
            // Setup
            // Verify
            expectedException.expect(SQLException.class);
            expectedException.expect(is(hasErrorCode(42102)));

            // Exercise
            Object bugId = 1;
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT * FROM Bugs";
                if (bugId != null || !bugId.toString().isEmpty()) {
                    sql += "WHERE bug_id = " + Integer.valueOf(bugId.toString());
                }
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    // 例外が発生して、ここは処理が行われない
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw e;
            }
        }

    }

}
