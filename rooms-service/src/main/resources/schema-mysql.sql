USE `rooms-db`;

Create table  if not exists rooms (
                                     id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     room_id VARCHAR(36),
    room_type VARCHAR(50),
    room_size INTEGER(50),
    room_number INTEGER(50),
    price INTEGER(50)
    );