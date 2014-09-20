package org.grimrose.sqlap22.domain.java;


import com.google.common.base.Optional;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class NoCheckTest {

    private static Logger logger = LoggerFactory.getLogger(NoCheckTest.class);

    String user = "dbuser";
    String pass = "dbpassword";
    String database = "test";
    String server = "db.example.com";

    @Ignore
    public void _診断せずに判断する_in_Java_without_try_with_resources() throws Exception {
        // Setup
        String url = "jdbc:mysql://" + server + "/" + database;

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

    @Ignore
    public void _リズムを維持する() throws Exception {
        // Setup
        long id = 1;
        String status = "OPEN";

        // Exercise
        List<Bug> bugs = findByAssignedByAndStatus(id, status);

        // Verify
        assertThat(bugs.isEmpty(), is(not(true)));
    }

    List<Bug> findByAssignedByAndStatus(long id, String status) {
        String url = "jdbc:mysql://" + server + "/" + database;
        List<Bug> bugs = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String sql = "SELECT bug_id, summary, date_reported FROM Bugs" +
                    " WHERE assigned_to = ? AND status = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setString(2, status);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Bug b = new Bug();
                        b.setBugId(rs.getLong("bug_id"));
                        b.setSummary(Optional.fromNullable(rs.getString("summary")));
                        b.setDataReported(new Date(rs.getDate("date_reported").getTime()));
                        bugs.add(b);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return bugs;
    }

}
