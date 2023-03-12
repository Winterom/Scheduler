CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY ,
    email VARCHAR(50) NOT NULL UNIQUE ,
    createdAt TIMESTAMPTZ not null,
    updatedAt TIMESTAMPTZ default current_timestamp
)