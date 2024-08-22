package com.demo.cars.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookingNotFoundException extends ClientException {
    public BookingNotFoundException(String message) {
        super(message);
    }
}
