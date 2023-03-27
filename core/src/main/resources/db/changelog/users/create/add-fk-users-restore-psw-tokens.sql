ALTER TABLE restore_psw_token
    ADD CONSTRAINT fk_users_restore_psw
        FOREIGN KEY (id)
            REFERENCES users(id)