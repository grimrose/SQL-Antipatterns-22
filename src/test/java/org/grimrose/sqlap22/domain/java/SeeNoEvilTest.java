package org.grimrose.sqlap22.domain.java;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class SeeNoEvilTest {

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
