CREATE TABLE user (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL CHECK (LENGTH(password) >= 8 AND LENGTH(password) <= 255),
    creation_date timestamp NOT NULL ,
    update_date timestamp NOT NULL ,

    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


