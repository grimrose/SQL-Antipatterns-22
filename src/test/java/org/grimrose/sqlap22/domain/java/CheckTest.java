package org.grimrose.sqlap22.domain.java;

import com.google.common.base.Optional;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class CheckTest {

    private static Logger logger = LoggerFactory.getLogger(CheckTest.class);

    private static final String URL = "jdbc:log4jdbc:h2:file:./db/default";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    @ClassRule
    public static TestHelper helper = new TestHelper(URL, USER, PASSWORD);

    @Test
    public void _リズムを維持する() throws Exception {
        // Setup
        long id = helper.insertAccount("Java");
        String status = "OPEN";
        helper.insertBug("dummy", id, id, status);

        // Exercise
        List<Bug> bugs = findByAssignedByAndStatus(id, status);

        // Verify
        assertThat(bugs.isEmpty(), is(not(true)));
    }

    List<Bug> findByAssignedByAndStatus(long id, String status) {
        List<Bug> bugs = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT bug_id, summary, data_reported FROM Bugs" +
                    " WHERE assigned_to = ? AND status = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setString(2, status);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Bug b = new Bug();
                        b.setBugId(rs.getLong("bug_id"));
                        b.setSummary(Optional.fromNullable(rs.getString("summary")));
                        b.setDataReported(new Date(rs.getDate("data_reported").getTime()));
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
