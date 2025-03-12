package com.demo.cars.service;

import com.demo.cars.dto.BookingDto;
import com.demo.cars.model.booking.BookingPostRequest;
import com.demo.cars.model.booking.BookingUpdateRequest;

import java.util.List;

public interface BookingService {
    BookingDto addNewRide(BookingPostRequest request);

    List<BookingDto> getAllRides();

    BookingDto getRideById(long id);

    List<BookingDto> getAllUserRidesByStatus(long userId, String status);

    List<BookingDto> getAllUserRides(long userId);

    List<BookingDto> getCarRideHistory(long carId);

    List<BookingDto> getCarRidesByStatus(long carId, String status);

    BookingDto updateRideStatus(long id, BookingUpdateRequest request);

    long updateRidesStatus(long userId);

    void deleteRide(long id);
}
