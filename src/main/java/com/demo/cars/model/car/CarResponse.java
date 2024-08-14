package com.demo.cars.model.car;

import java.time.Year;

public record CarResponse(
        String carClass,
        String brand,
        String model,
        Byte numberOfSeats,
        Year yearOfProduction,
        String plateNumber,
        Boolean isAvailable,
        Integer dailyFee
) {
}
