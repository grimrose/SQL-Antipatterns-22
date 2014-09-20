package org.grimrose.sqlap22.domain.java;


import com.google.common.base.Optional;

import java.util.Date;

public class Bug {

    long bugId;
    Date dataReported;
    Optional<String> summary;
    long reportedBy;
    Optional<Long> assignedTo;
    BugStatus status;

    public long getBugId() {
        return bugId;
    }

    public void setBugId(long bugId) {
        this.bugId = bugId;
    }

    public Date getDataReported() {
        return dataReported;
    }

    public void setDataReported(Date dataReported) {
        this.dataReported = dataReported;
    }

    public Optional<String> getSummary() {
        return summary;
    }

    public void setSummary(Optional<String> summary) {
        this.summary = summary;
    }

    public long getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(long reportedBy) {
        this.reportedBy = reportedBy;
    }

    public Optional<Long> getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Optional<Long> assignedTo) {
        this.assignedTo = assignedTo;
    }

    public BugStatus getStatus() {
        return status;
    }

    public void setStatus(BugStatus status) {
        this.status = status;
    }

}
