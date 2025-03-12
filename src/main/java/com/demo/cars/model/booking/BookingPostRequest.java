package com.demo.cars.model.booking;

import java.sql.Timestamp;
import java.util.Optional;

// startDate = now, endDate(Optional) - when ride is finished, status - active by default, change when ride is finished/stopped.
public record BookingPostRequest(
        long userId,
        long carId,
        Optional<Timestamp> endDate, // not sure about Optional
        Optional<String> status // here too
) {
}
