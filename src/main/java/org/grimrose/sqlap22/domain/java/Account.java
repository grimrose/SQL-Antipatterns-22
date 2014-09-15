package org.grimrose.sqlap22.domain.java;


import com.google.common.base.Optional;

public class Account {
    private long accountId;
    private Optional<String> accountName;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Optional<String> getAccountName() {
        return accountName;
    }

    public void setAccountName(Optional<String> accountName) {
        this.accountName = accountName;
    }
}
