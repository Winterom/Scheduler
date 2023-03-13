CREATE TABLE roles_authorities
(
    authority_id BIGINT not null,
    role_id      BIGINT not null,
    PRIMARY KEY (authority_id, role_id)
)