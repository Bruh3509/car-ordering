package com.demo.cars.exception.payment;

import com.demo.cars.exception.ClientException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaymentNotFoundException extends ClientException {
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
