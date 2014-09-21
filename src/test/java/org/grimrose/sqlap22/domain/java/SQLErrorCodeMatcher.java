package org.grimrose.sqlap22.domain.java;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Ignore;

import java.sql.SQLException;
import java.text.MessageFormat;


public class SQLErrorCodeMatcher extends TypeSafeMatcher<SQLException> {

    private final int errorCode;

    public SQLErrorCodeMatcher(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    protected boolean matchesSafely(SQLException e) {
        return errorCode == e.getErrorCode();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(MessageFormat.format("expect SQL error code:[{0}]", errorCode));
    }


    public static SQLErrorCodeMatcher hasErrorCode(int expected) {
        return new SQLErrorCodeMatcher(expected);
    }
}
