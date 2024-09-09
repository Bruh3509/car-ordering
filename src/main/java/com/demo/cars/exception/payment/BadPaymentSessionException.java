package com.demo.cars.exception.payment;

import com.demo.cars.exception.ClientException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BadPaymentSessionException extends ClientException {
    public BadPaymentSessionException(String message) {
        super(message);
    }
}
