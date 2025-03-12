package com.demo.cars.model.booking;

import java.sql.Timestamp;

public record BookingUpdateRequest(
        Timestamp endDate,
        String status
) {
}
