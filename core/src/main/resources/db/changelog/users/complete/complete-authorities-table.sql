INSERT INTO authorities (title, description,e_authorities, createdat, updatedat) VALUES
      ('Пользователи чтение', 'Право на получение информации об пользователях','USERS_DATA_READ',current_timestamp,current_timestamp),
      ('Пользователи запись', 'Право на изменении информации об пользователях','USER_DATA_READ_WRITE',current_timestamp,current_timestamp),
      ('Глобальные настройки чтение', 'Право на чтение информации об настройках программы','GLOBAL_SETTINGS_READ',current_timestamp,current_timestamp),
      ('Глобальные настройки запись', 'Права на изменении информации об настройках программы','GLOBAL_SETTINGS_READ_WRITE',current_timestamp,current_timestamp)
