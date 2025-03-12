package com.demo.cars.repository;

import com.demo.cars.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserIdAndStatus(long userId, String status);

    List<Booking> findByUserId(long userId);

    List<Booking> findByCarIdAndStatus(long carId, String status);

    List<Booking> findByCarId(long carId);
}
