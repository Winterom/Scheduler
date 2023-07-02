CREATE TABLE change_psw_token (
    id BIGSERIAL unique not null primary key ,
    token VARCHAR(100) UNIQUE NOT NULL ,
    createdAt TIMESTAMPTZ default current_timestamp,
    expired TIMESTAMPTZ NOT NULL
)
