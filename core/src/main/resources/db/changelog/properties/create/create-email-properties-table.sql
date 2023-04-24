CREATE TABLE email_properties(
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(20),
    description TEXT,
    email VARCHAR(100) not null,
    password VARCHAR(250) not null,
    is_enabled BOOLEAN not null,
    smtpHost VARCHAR(300),
    smtpEnabledSSL BOOLEAN,
    smtpEnabledTLS BOOLEAN,
    smtpRequireAuth BOOLEAN,
    smtpPortSSL INTEGER,
    smtpPortTLS INTEGER,
    smtpTransportProtocol VARCHAR(100),
    incomingServerType VARCHAR(20),
    incomingIMAPServer VARCHAR(100),
    incomingEnabledSSL BOOLEAN,
    incomingPortSSL INTEGER
)