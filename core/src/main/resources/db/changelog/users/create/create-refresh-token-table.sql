CREATE TABLE refresh_tokens(
    id BIGINT NOT NULL PRIMARY KEY,
    expired TIMESTAMPTZ NOT NULL,
    token VARCHAR(250)
)