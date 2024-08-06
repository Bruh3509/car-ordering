package com.demo.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private Timestamp creationDate;
    private String passportId;
    private String drivingLicenseId;

    public UserDto(Long id, String firstname, String lastname, String email, String phoneNumber, Timestamp creationDate, String passportId, String drivingLicenseId) {
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
