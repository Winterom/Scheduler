ALTER TABLE employees
    ADD CONSTRAINT fk_users_employees
        FOREIGN KEY (userId)
            REFERENCES users(id)