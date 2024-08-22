CREATE TABLE car (
    id BIGINT NOT NULL PRIMARY KEY,
    car_class VARCHAR(255),
    brand VARCHAR(255),
    model VARCHAR(255),
    number_of_seats TINYINT,
    year_of_production YEAR,
    plate_number VARCHAR(255) UNIQUE,
    is_available BIT(1) DEFAULT b'0',
    daily_fee INT
);
