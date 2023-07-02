CREATE TABLE refresh_tokens(
    id BIGSERIAL unique not null primary key ,
    expired TIMESTAMPTZ NOT NULL,
    token VARCHAR(250)
)