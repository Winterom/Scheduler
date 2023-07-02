CREATE TABLE email_approved_token(
    userId BIGSERIAL unique not null primary key ,
    token VARCHAR(100) UNIQUE,
    createdAt TIMESTAMPTZ default current_timestamp,
    expired TIMESTAMPTZ NOT NULL
)