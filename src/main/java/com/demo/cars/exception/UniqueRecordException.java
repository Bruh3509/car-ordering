package com.demo.cars.exception;

public class UniqueRecordException extends RuntimeException {
    public UniqueRecordException(String message) {
        super(message);
    }
}
