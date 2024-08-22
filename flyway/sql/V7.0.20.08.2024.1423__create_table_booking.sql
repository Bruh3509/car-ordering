CREATE TABLE booking (
    id BIGINT NOT NULL PRIMARY KEY,
    user_id BIGINT,
    car_id BIGINT,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    status VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES _user(id),
    FOREIGN KEY (car_id) REFERENCES car(id)
);
