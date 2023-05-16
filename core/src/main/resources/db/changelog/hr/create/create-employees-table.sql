CREATE TABLE employees(
    id BIGSERIAL PRIMARY KEY,
    userId BIGINT not null,
    name VARCHAR(50) not null,
    surname VARCHAR(50) not null,
    lastname VARCHAR(50)
)