package com.demo.cars.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarDto {
    PlaceDto placeDto;
    String carClass;
    String brand;
    String model;
    Byte numberOfSeats;
    Year yearOfProduction;
    String plateNumber;
    Boolean isAvailable;
    Integer minuteFee;
}
