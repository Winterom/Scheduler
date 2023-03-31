INSERT INTO email_properties (type, description, email, password,
                              isenabled, smtphost, smtpenabledssl,
                              smtpenabledtls, smtprequireauth,
                              smtpportssl, smtpporttls,
                              smtptransportprotocol,
                              incomingservertype, incomingimapserver,
                              incomingenabledssl, incomingportssl) VALUES
         ('ADMIN_SENDER','Почта с которой производится рассылка уведомлений о смене пароля, истечении срока действия пароля, блокировки аккоунта и так далее',
          'test.marketplace20@gmail.com','nxmiagjiyzcwhqgw',true,'smtp.gmail.com',true,
          true,true,465,587,'SMTP','IMAP','imap.gmail.com',
          true,993)