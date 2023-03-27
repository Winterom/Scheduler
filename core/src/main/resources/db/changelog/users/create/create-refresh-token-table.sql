CREATE TABLE refresh_tokens(
    id BIGINT PRIMARY KEY,
    expired TIMESTAMPTZ NOT NULL,
    token VARCHAR(250)
)