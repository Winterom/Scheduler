CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY ,
    is_catalog BOOLEAN not null,
    catalog BIGSERIAL REFERENCES roles,
    title VARCHAR(100) not null ,
    description VARCHAR(300) not null,
    createdAt TIMESTAMPTZ not null,
    updatedAt TIMESTAMPTZ default current_timestamp,
    status VARCHAR(20) not null ,
    modifyBy BIGSERIAL not null
)