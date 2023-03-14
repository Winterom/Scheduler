ALTER TABLE refresh_tokens
    ADD CONSTRAINT fk_users_refresh_token
        FOREIGN KEY (id)
            REFERENCES users(id)