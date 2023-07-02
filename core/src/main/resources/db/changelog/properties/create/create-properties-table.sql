CREATE TABLE properties (
    clazz VARCHAR(100) PRIMARY KEY unique not null ,
    property jsonb NOT NULL
)