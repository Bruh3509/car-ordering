package com.demo.cars.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "_user")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true, callSuper = false)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Payment> payments = new LinkedList<>();

    public User(
            Long id,
            String firstname,
            String lastname,
            String email,
            String phoneNumber,
            Timestamp creationDate,
            String passportId,
            String drivingLicenseId
    ) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.creationDate = creationDate;
        this.passportId = passportId;
        this.drivingLicenseId = drivingLicenseId;
    }
}
