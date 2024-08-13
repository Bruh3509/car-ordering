package com.demo.cars.repository;

import com.demo.cars.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

    boolean existsByPassportIdAndIdNot(String passportId, Long id);

    boolean existsByDrivingLicenseIdAndIdNot(String drivingLicenseId, Long id);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPassportId(String passportId);

    boolean existsByDrivingLicenseId(String drivingLicenseId);
}
