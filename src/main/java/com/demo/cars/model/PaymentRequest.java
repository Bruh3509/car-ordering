package com.demo.cars.model;

public record PaymentRequest(
        Long userId,
        Long sessionId,
        String status,
        String url,
        Integer paymentAmount,
        String type
) {
}
