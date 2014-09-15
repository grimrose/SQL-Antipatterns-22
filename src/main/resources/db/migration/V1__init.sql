CREATE TABLE Accounts (
  account_id   SERIAL PRIMARY KEY,
  account_name VARCHAR(20)
);

CREATE TABLE BugStatus (
  status VARCHAR(20) PRIMARY KEY
);

CREATE TABLE Bugs (
  bug_id        SERIAL PRIMARY KEY,
  data_reported DATE NOT NULL,
  summary       VARCHAR(80),
  reported_by   BIGINT NOT NULL,
  assigned_to   BIGINT,
  status        VARCHAR(20) NOT NULL DEFAULT 'NEW',
  FOREIGN KEY (reported_by) REFERENCES Accounts (account_id),
  FOREIGN KEY (assigned_to) REFERENCES Accounts (account_id),
  FOREIGN KEY (status) REFERENCES BugStatus(status)
);
