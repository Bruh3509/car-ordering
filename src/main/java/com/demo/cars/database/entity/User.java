package com.demo.cars.database.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Table(name = "_user")
@Getter
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String firstname;
    private String lastname;

    @Column(unique = true)
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "passport_id", unique = true)
    private String passportId;

    @Column(name = "driving_license_id", unique = true)
    private String drivingLicenseId;

    public User() {
    }

    public User(String firstname,
                String lastname,
                String email,
                String phoneNumber,
                Timestamp creationDate,
                String passportId,
                String drivingLicenseId
    ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.creationDate = creationDate;
        this.passportId = passportId;
        this.drivingLicenseId = drivingLicenseId;
    }
}
