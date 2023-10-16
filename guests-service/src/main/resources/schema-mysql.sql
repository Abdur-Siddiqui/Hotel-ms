USE `guests-db`;

create table if not exists guests (
                                      id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                      guest_id VARCHAR(36),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email_address VARCHAR(50),
    street_address VARCHAR (50),
    city VARCHAR (50),
    province VARCHAR (50),
    country VARCHAR (50),
    postal_code VARCHAR (50),
    payment VARCHAR (50)
    );
