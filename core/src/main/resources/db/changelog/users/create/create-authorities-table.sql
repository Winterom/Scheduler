CREATE TABLE authorities (
    id BIGSERIAL primary key ,
    title VARCHAR(50) not null ,
    is_catalog BOOLEAN not null,
    catalog BIGSERIAL REFERENCES authorities,
    description VARCHAR(300) not null ,
    e_authorities VARCHAR(50),
    createdAt TIMESTAMPTZ not null,
    updatedAt TIMESTAMPTZ default current_timestamp,
    modifyBy VARCHAR(50) DEFAULT 'SYSTEM'
)