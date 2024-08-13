package com.demo.cars.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClientException extends RuntimeException {
    public ClientException(String message) {
        super(message);
    }
}
