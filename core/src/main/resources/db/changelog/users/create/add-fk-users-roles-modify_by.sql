ALTER TABLE roles
    ADD CONSTRAINT fk_roles_users_modify_by
        FOREIGN KEY (modifyby)
            REFERENCES roles(id);
