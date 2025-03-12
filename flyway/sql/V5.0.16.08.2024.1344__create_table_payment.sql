CREATE TABLE payment (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    user_id BIGINT,
    session_id BIGINT,
    status VARCHAR(255),
    url VARCHAR(255),
    payment_amount INT,
    type VARCHAR(255),
    payment_date DATETIME,
    FOREIGN KEY (user_id) REFERENCES _user(id)
);
