package com.demo.cars.database.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Entity
@Table(name = "_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String firstname;
    String lastname;

    @Column(unique = true)
    String email;

    @Column(name = "phone_number", unique = true)
    String phoneNumber;

    @Column(name = "creation_date")
    Timestamp creationDate;

    @Column(name = "passport_id", unique = true)
    String passportId;

    @Column(name = "driving_license_id", unique = true)
    String drivingLicenseId;
}
