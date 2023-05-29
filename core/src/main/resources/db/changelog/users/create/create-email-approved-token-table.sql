CREATE TABLE email_approved_token(
    userId BIGINT PRIMARY KEY,
    token VARCHAR(100) UNIQUE,
    expired TIMESTAMPTZ NOT NULL
)