package com.demo.cars.model.payment;

public record PaymentRequest(
        Long userId,
        String sessionId,
        String status,
        String url,
        Integer paymentAmount,
        String type
) {
}
