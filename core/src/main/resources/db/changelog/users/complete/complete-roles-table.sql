INSERT INTO roles (title, description, createdat,is_catalog,parent_id,modifyby,status) VALUES
    ('ROOT','ROOT',current_timestamp,true,null,1,'ACTIVE'),
    ('Служебные','Служебные роли',current_timestamp,true,1,1,'ACTIVE'),
    ('Администраторы','Роли администраторов',current_timestamp,true,1,1,'ACTIVE'),
    ('Регистратура','Роли модуля регистратуры',current_timestamp,true,1,1,'ACTIVE'),
    ('Управление персоналом','Роли сотрудников кадровой службы',current_timestamp,true,1,1,'ACTIVE'),
    ('Новый сотрудник','Роль по умолчанию назначаемая новым сотрудникам при создании аккаунта',current_timestamp,false,2,1,'ACTIVE'),
    ('СуперПользователь','Имеет доступ ко всем модулям программы с неограниченными правами',current_timestamp,false,3,1,'ACTIVE'),
    ('Администратор','Пользователь имеющий доступ на чтение и запись настроек программы, данных о пользователях', current_timestamp,false,3,1,'ACTIVE'),
    ('Работник регистратуры','Работник имеющий доступ на чтение и запись к модулю расписания', current_timestamp,false,4,1,'ACTIVE'),
    ('Менеджер по персоналу','Менеджер по персоналу', current_timestamp,false,5,1,'ACTIVE')

