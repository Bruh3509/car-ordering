package com.demo.cars.database.exception;

public class UniqueRecordException extends RuntimeException {
    public UniqueRecordException(String message) {
        super(message);
    }
}
