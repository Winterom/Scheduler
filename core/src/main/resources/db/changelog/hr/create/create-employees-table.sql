CREATE TABLE employees(
    id BIGSERIAL unique not null primary key ,
    userId BIGINT not null,
    name VARCHAR(50) not null,
    surname VARCHAR(50) not null,
    lastname VARCHAR(50)
)