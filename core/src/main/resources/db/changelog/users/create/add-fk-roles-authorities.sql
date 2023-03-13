ALTER TABLE roles_authorities
    ADD CONSTRAINT fk_roles_authorities
        FOREIGN KEY (role_id)
            REFERENCES roles(id);
ALTER TABLE roles_authorities
    ADD CONSTRAINT fk_authorities_roles
        FOREIGN KEY (authority_id)
            REFERENCES authorities(id)