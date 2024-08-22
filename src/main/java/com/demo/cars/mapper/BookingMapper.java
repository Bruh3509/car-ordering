package com.demo.cars.mapper;

import com.demo.cars.dto.BookingDto;
import com.demo.cars.entity.Booking;
import com.demo.cars.model.booking.BookingPostRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring", imports = {Timestamp.class, Instant.class})
public interface BookingMapper {
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "car.id", source = "carId")
    @Mapping(target = "startDate", expression = "java(Timestamp.from(Instant.now()))")
    @Mapping(target = "endDate",
            expression = "java(request.endDate().orElseGet(() -> Timestamp.valueOf(\"2038-01-19 03:14:07\")))")
    @Mapping(target = "status", expression = "java(request.status().orElseGet(() -> \"ACTIVE\"))")
    Booking requestToEntity(BookingPostRequest request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "carId", source = "car.id")
    BookingDto entityToDto(Booking entity);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "carId", source = "car.id")
    List<BookingDto> entityToDto(List<Booking> entities);
}
