package com.demo.cars.kafka.model;

public record MyKafkaMessage(
        Long userId,
        String paymentLink,
        String email
) {
}
