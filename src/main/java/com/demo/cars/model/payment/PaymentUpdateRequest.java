package com.demo.cars.model.payment;

public record PaymentUpdateRequest(
        String status,
        String url,
        Integer paymentAmount,
        String type
) {
}
