CREATE TABLE authorities (
    id BIGSERIAL unique not null primary key ,
    title VARCHAR(50) not null ,
    is_catalog BOOLEAN not null,
    catalog BIGSERIAL REFERENCES authorities,
    description VARCHAR(300) not null ,
    e_authorities VARCHAR(50)
)