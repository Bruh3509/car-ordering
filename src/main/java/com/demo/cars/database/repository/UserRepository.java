package com.demo.cars.database.repository;

import com.demo.cars.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPassportId(String passportId);
    boolean existsByDrivingLicenseId(String drivingLicenseId);
}
