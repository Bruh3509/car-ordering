package com.demo.cars.model.car;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Year;

public record CarRequest(
        String carClass,
        String brand,
        String model,
        Byte numberOfSeats,
        @JsonProperty
        Year yearOfProduction,
        String plateNumber,
        Boolean isAvailable,
        Integer dailyFee
) {
}
