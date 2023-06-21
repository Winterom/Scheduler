INSERT INTO authorities (title, description,e_authorities, createdat, updatedat, is_catalog, catalog) VALUES
      ('ROOT','ROOT',NULL,current_timestamp,current_timestamp,true,1),
      ('Служебные','Служебные права',null,current_timestamp,current_timestamp,true,1),
      ('Пользователи','Права на управление пользователями',null,current_timestamp,current_timestamp,true,1),
      ('Настройки','Права на изменение настроек',null,current_timestamp,current_timestamp,true,1),
      ('Собственный профиль', 'Чтение и запись изменений собственного профиля','PROFILE_READ_WRITE',current_timestamp,current_timestamp,false,2),
      ('Пользователи чтение', 'Право на получение информации об пользователях','USERS_DATA_READ',current_timestamp,current_timestamp,false,3),
      ('Пользователи запись', 'Право на изменении информации об пользователях','USERS_DATA_READ_WRITE',current_timestamp,current_timestamp,false,3),
      ('Глобальные настройки чтение', 'Право на чтение информации об настройках программы','GLOBAL_SETTINGS_READ',current_timestamp,current_timestamp,false,4),
      ('Глобальные настройки запись', 'Права на изменении информации об настройках программы','GLOBAL_SETTINGS_READ_WRITE',current_timestamp,current_timestamp,false,4)

