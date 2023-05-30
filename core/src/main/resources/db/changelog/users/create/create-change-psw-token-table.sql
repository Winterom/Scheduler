CREATE TABLE change_psw_token (
    id BIGINT PRIMARY KEY,
    token VARCHAR(100) UNIQUE NOT NULL ,
    createdAt TIMESTAMPTZ default current_timestamp,
    expired TIMESTAMPTZ NOT NULL
)
