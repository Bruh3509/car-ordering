package com.demo.cars.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaymentNotFoundException extends ClientException {
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
