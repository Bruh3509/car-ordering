package com.demo.cars.entity;

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

    @Column(unique = true)
    String phoneNumber;

    Timestamp creationDate;

    @Column(unique = true)
    String passportId;

    @Column(unique = true)
    String drivingLicenseId;
}
