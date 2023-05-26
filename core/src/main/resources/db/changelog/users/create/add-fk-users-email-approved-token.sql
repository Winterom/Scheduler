ALTER TABLE email_approved_token
    ADD CONSTRAINT fk_users_email_approved_token
        FOREIGN KEY (userId)
            REFERENCES users(id);