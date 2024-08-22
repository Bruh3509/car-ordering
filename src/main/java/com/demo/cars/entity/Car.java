package com.demo.cars.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
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

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    List<Booking> booking = new ArrayList<>();

    public Car(
            Long id,
            String carClass,
            String brand,
            String model,
            Byte numberOfSeats,
            Year yearOfProduction,
            String plateNumber,
            Boolean isAvailable,
            Integer dailyFee
    ) {
        this.id = id;
        this.carClass = carClass;
        this.brand = brand;
        this.model = model;
        this.numberOfSeats = numberOfSeats;
        this.yearOfProduction = yearOfProduction;
        this.plateNumber = plateNumber;
        this.isAvailable = isAvailable;
        this.dailyFee = dailyFee;
    }
}
