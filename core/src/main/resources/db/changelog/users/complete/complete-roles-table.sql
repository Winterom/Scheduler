INSERT INTO roles (title, description, createdat) VALUES
  ('SuperUser','Имеет доступ ко всем модулям программы с неограниченными правами',current_timestamp),
  ('Administrator','Пользователь имеющий доступ на чтение и запись к глобальным настройкам программы', current_timestamp),
  ('Registration Manager','Работник имеющий доступ на чтение  и запись  к модулю расписания', current_timestamp),
  ('Personal Manager','Менеджер по персоналу', current_timestamp)