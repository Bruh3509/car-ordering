package com.demo.cars.model;

public record ErrorResponse(
        Class<?> excClass,
        String message
) {
}
