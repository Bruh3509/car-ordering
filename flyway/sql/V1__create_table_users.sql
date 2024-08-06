CREATE TABLE users (
    id INT NOT NULL PRIMARY KEY,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email  VARCHAR(255) UNIQUE,
    phone_number VARCHAR(255) UNIQUE,
    creation_date TIMESTAMP,
    passport_id VARCHAR(255) UNIQUE,
    driving_license_id VARCHAR(255) UNIQUE
);
