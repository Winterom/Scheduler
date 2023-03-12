ALTER TABLE users_roles
    ADD CONSTRAINT fk_roles_users
        FOREIGN KEY (role_id)
            REFERENCES roles(id);
ALTER TABLE users_roles
    ADD CONSTRAINT fk_users_roles
        FOREIGN KEY (user_id)
            REFERENCES users(id);