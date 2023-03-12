CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY ,
    title VARCHAR(100),
    description VARCHAR(300),
    createdAt TIMESTAMPTZ not null,
    updatedAt TIMESTAMPTZ default current_timestamp

)