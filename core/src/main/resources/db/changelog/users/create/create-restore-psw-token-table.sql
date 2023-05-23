CREATE TABLE restore_psw_token (
    id BIGINT PRIMARY KEY,
    token VARCHAR(40) unique not null,
    expire TIMESTAMPTZ not null
)
