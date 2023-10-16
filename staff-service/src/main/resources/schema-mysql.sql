USE `staff-db`;


CREATE TABLE IF NOT EXISTS staff (
                                     id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     staff_id VARCHAR(36),
    employee_first_name VARCHAR(50),
    employee_last_name VARCHAR(50),
    employee_email_address VARCHAR(50),
    employee_city VARCHAR(50),
    employee_postal_code VARCHAR(50),
    reservation_date_made VARCHAR(50),
    employee_phone_number VARCHAR(50),
    employee_job_position VARCHAR(50)
    );
