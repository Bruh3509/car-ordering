package com.demo.cars.model.user;

public record UserRequest(
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        String passportId,
        String drivingLicenseId
) {
}
