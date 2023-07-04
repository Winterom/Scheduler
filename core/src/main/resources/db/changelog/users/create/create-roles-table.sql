CREATE TABLE roles (
    id BIGSERIAL unique not null primary key  ,
    is_catalog BOOLEAN not null,
    parent_id BIGINT,
    title VARCHAR(100) not null ,
    description VARCHAR(300) not null,
    createdAt TIMESTAMPTZ not null,
    updatedAt TIMESTAMPTZ default current_timestamp,
    status VARCHAR(20) not null ,
    modifyBy BIGSERIAL not null
)