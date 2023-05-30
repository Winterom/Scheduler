CREATE TABLE email_approved_token(
    userId BIGINT PRIMARY KEY,
    token VARCHAR(100) UNIQUE,
    createdAt TIMESTAMPTZ default current_timestamp,
    expired TIMESTAMPTZ NOT NULL
)