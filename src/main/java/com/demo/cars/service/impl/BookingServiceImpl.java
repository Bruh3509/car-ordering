package com.demo.cars.service.impl;

import com.demo.cars.dto.BookingDto;
import com.demo.cars.exception.BookingNotFoundException;
import com.demo.cars.mapper.BookingMapper;
import com.demo.cars.model.booking.BookingPostRequest;
import com.demo.cars.model.booking.BookingUpdateRequest;
import com.demo.cars.repository.BookingRepository;
import com.demo.cars.service.BookingService;
import com.demo.cars.util.enums.Status;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingServiceImpl implements BookingService {
    BookingRepository repository;
    BookingMapper mapper;

    @Override
    public BookingDto addNewRide(BookingPostRequest request) {
        return mapper.entityToDto(
                repository.save(
                        mapper.requestToEntity(request)
                )
        );
    }

    @Override
    public List<BookingDto> getAllRides() {
        return mapper.entityToDto(repository.findAll());
    }

    @Override
    public BookingDto getRideById(long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow(BookingNotFoundException::new);
    }

    @Override
    public List<BookingDto> getAllUserRidesByStatus(long userId, String status) {
        return mapper.entityToDto(
                repository.findByUserIdAndStatus(userId, status)
        );
    }

    @Override
    public List<BookingDto> getAllUserRides(long userId) {
        return mapper.entityToDto(
                repository.findByUserId(userId) // rename + var
        );
    }

    @Override
    public List<BookingDto> getCarRideHistory(long carId) {
        return mapper.entityToDto(
                repository.findByCarId(carId)
        );
    }

    @Override
    public List<BookingDto> getCarRidesByStatus(long carId, String status) {
        return mapper.entityToDto(
                repository.findByCarIdAndStatus(carId, status)
        );
    }

    @Override
    public BookingDto updateRideStatus(long id, BookingUpdateRequest request) {
        var booking = repository.findById(id)
                .orElseThrow(BookingNotFoundException::new);

        booking.setEndDate(request.endDate());
        booking.setStatus(request.status());

        return mapper.entityToDto(
                repository.save(booking)
        );
    }

    @Override
    public long updateRidesStatus(long userId) {
        var rides = repository.findByUserIdAndStatus(userId, Status.COMPLETE.name());

        for (var ride : rides)
            ride.setStatus(Status.PAYED.name());

        repository.saveAll(rides);
        return 0;
    }

    @Override
    public void deleteRide(long id) {
        if (!repository.existsById(id))
            throw new BookingNotFoundException();

        repository.deleteById(id);
    }
}
