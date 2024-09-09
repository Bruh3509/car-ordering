package com.demo.cars.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true, callSuper = false)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "branch_id")
    Place place;

    String carClass;
    String brand;
    String model;
    Byte numberOfSeats;
    Year yearOfProduction;

    @Column(unique = true)
    String plateNumber;

    Boolean isAvailable;
    @Column(name = "daily_fee")
    Integer minuteFee;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    List<Booking> booking = new ArrayList<>();

    public Car(
            Long id,
            Place place,
            String carClass,
            String brand,
            String model,
            Byte numberOfSeats,
            Year yearOfProduction,
            String plateNumber,
            Boolean isAvailable,
            Integer minuteFee
    ) {
        this.id = id;
        this.place = place;
        this.carClass = carClass;
        this.brand = brand;
        this.model = model;
        this.numberOfSeats = numberOfSeats;
        this.yearOfProduction = yearOfProduction;
        this.plateNumber = plateNumber;
        this.isAvailable = isAvailable;
        this.minuteFee = minuteFee;
    }
}
