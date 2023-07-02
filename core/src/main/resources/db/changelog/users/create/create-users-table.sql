CREATE TABLE users (
    id BIGSERIAL unique not null primary key ,
    email VARCHAR(50) NOT NULL UNIQUE ,
    is_email_verified BOOLEAN default false,
    phone VARCHAR(50) UNIQUE ,
    is_phone_verified BOOLEAN default false,
    password VARCHAR(100) NOT NULL,
    credential_expired TIMESTAMPTZ not null,
    e_status VARCHAR(20) NOT NULL,
    createdAt TIMESTAMPTZ not null,
    updatedAt TIMESTAMPTZ default current_timestamp
)