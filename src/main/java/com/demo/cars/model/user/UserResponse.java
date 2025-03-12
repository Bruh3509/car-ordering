package com.demo.cars.model.user;

public record UserResponse(
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        String passportId,
        String drivingLicenseId
) {
}
