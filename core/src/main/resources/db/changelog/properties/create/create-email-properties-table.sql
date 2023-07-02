CREATE TABLE email_properties(
    id BIGSERIAL unique not null primary key ,
    type VARCHAR(20),
    description TEXT,
    email VARCHAR(100) not null,
    password VARCHAR(250) not null,
    is_enabled BOOLEAN not null,
    smtpHost VARCHAR(300),
    smtpProtocol VARCHAR(4) not null,
    smtpRequireAuth BOOLEAN not null,
    smtpPortSSL INTEGER,
    smtpPortTLS INTEGER,
    incomingServerType VARCHAR(20),
    incomingIMAPServer VARCHAR(100),
    incomingEnabledSSL BOOLEAN,
    incomingPortSSL INTEGER
)