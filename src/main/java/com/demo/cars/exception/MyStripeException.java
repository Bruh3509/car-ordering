package com.demo.cars.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MyStripeException extends ClientException {
    public MyStripeException(String message) {
        super(message);
    }
}
