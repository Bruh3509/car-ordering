package com.demo.cars.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends ClientException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
