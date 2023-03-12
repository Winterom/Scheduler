CREATE TABLE authorities (
    id BIGSERIAL primary key ,
    title VARCHAR(50),
    description VARCHAR(300),
    createdAt TIMESTAMPTZ not null,
    updatedAt TIMESTAMPTZ default current_timestamp
)