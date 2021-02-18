CREATE TABLE IF NOT EXISTS Artisan(
    id INT,
    first_name VARCHAR (100) NOT NULL,
    last_name VARCHAR (100) NOT NULL,
    category VARCHAR (10) NOT NULL,
    password VARCHAR (100) NOT NULL,
    thumbnail VARCHAR (100) NOT NULL,
    email_address VARCHAR (50) NOT NULL,
    phone_number VARCHAR (15) NOT NULL,
    gender VARCHAR (5) NOT NULL,
    country VARCHAR (15) NOT NULL
);

CREATE TABLE client(
     id INT,
     user_id INT NOT NULL,
     first_name VARCHAR (100) NOT NULL,
     last_name VARCHAR (100) NOT NULL,
     email_address VARCHAR (50) NOT NULL,
     phone_number VARCHAR (15) NOT NULL,
     gender VARCHAR (5) NOT NULL,
     delivery_address VARCHAR (100) NOT NULL
);

-- CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE artisan_seq
  MINVALUE 1
  MAXVALUE 1000000
  START WITH 1
  INCREMENT BY 1;

CREATE SEQUENCE client_seq
    MINVALUE 1
    MAXVALUE 1000000
    START WITH 1
    INCREMENT BY 1;


