INSERT INTO email_properties (type, description, email, password,
                              is_enabled, smtphost,
                              smtpProtocol, smtprequireauth,
                              smtpportssl, smtpporttls,
                              incomingservertype, incomingimapserver,
                              incomingenabledssl, incomingportssl) VALUES
         ('ADMIN_SENDER','Почта с которой производится рассылка уведомлений о смене пароля, истечении срока действия пароля, блокировки аккоунта и так далее',
          'test.marketplace20@gmail.com','nrfnxvozpeubwijm',true,'smtp.gmail.com','SSL'
          ,true,465,587,'IMAP','imap.gmail.com',
          true,993),
         ('REGISTER_SENDER','Почта с которой производится рассылка уведомлений о записи на прием, отмены записи и любых других изменений связанных с раписанием премов',
          'test.marketplace20old@gmail.com','nrfnxvozpeubwijm',false,'smtp.gmail.com','SSL',true,465,587,'IMAP','imap.gmail.com',
          true,993),
         ('REGISTER_SENDER','Почта с которой производится рассылка уведомлений о записи на прием, отмены записи и любых других изменений связанных с раписанием премов',
          'test.marketplace2011@gmail.com','nrfnxvozpeubwijm',true,'smtp.gmail.com','SSL',true,465,587,'IMAP','imap.gmail.com',
          true,993)