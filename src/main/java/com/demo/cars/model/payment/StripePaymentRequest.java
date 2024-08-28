package com.demo.cars.model.payment;

public record StripePaymentRequest(
        Long userId,
        Long paymentAmount,
        String paymentMethodType, // PaymentMethodType enum will be created, consider upper case
        String currency
) {
}
