CREATE TABLE users_roles (
    user_id BIGINT not null,
    role_id BIGINT not null,
    PRIMARY KEY (user_id,role_id)
)