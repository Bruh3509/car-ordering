package com.demo.cars.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Year;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String carClass;
    String brand;
    String model;
    Byte numberOfSeats;
    Year yearOfProduction;

    @Column(unique = true)
    String plateNumber;

    Boolean isAvailable;
    Integer dailyFee;
}
