package com.demo.cars.database;

import com.demo.cars.dto.BookingDto;
import com.demo.cars.entity.Booking;
import com.demo.cars.entity.Car;
import com.demo.cars.entity.Place;
import com.demo.cars.entity.User;
import com.demo.cars.exception.BookingNotFoundException;
import com.demo.cars.mapper.BookingMapper;
import com.demo.cars.model.booking.BookingPostRequest;
import com.demo.cars.model.booking.BookingUpdateRequest;
import com.demo.cars.repository.BookingRepository;
import com.demo.cars.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import static com.demo.cars.util.PropertyUtil.SRID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private BookingMapper bookingMapper;
    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    void resetMockRules() {
        Mockito.reset(bookingRepository);
        Mockito.reset(bookingMapper);
    }

    @Test
    void testAddNewRide() {
        // arrange
        GeometryFactory factory = new GeometryFactory();
        var longitude = 10;
        var latitude = 10;
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        var place = new Place(
                1L,
                point
        );
        var user = new User(
                1L,
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                Timestamp.from(Instant.now()),
                "JASO1921AS",
                "JI1O90KA1"
        );
        var car = new Car(
                1L,
                place,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );
        var request = new BookingPostRequest(
                1,
                1,
                Optional.of(Timestamp.from(Instant.MAX)),
                Optional.of("ACTIVE")
        );
        var booking = new Booking(
                1L,
                user,
                car,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MAX),
                "ACTIVE"
        );
        var bookingDto = new BookingDto(
                1L,
                1L,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MAX),
                "ACTIVE"
        );

        // act
        when(bookingMapper.requestToEntity(request))
                .thenReturn(booking);
        when(bookingRepository.save(booking))
                .thenReturn(booking);
        when(bookingMapper.entityToDto(booking))
                .thenReturn(bookingDto);

        // assert
        assertEquals(bookingDto, bookingService.addNewRide(request));
    }

    @Test
    void testGetAllRides() {
        // arrange
        GeometryFactory factory = new GeometryFactory();
        var longitude = 10;
        var latitude = 10;
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        var place = new Place(
                1L,
                point
        );
        var user = new User(
                1L,
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                Timestamp.from(Instant.now()),
                "JASO1921AS",
                "JI1O90KA1"
        );
        var car = new Car(
                1L,
                place,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );
        var booking = new Booking(
                1L,
                user,
                car,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MAX),
                "ACTIVE"
        );
        var bookingDto = new BookingDto(
                1L,
                1L,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MAX),
                "ACTIVE"
        );

        // act
        when(bookingRepository.findAll())
                .thenReturn(List.of(booking));
        when(bookingMapper.entityToDto(List.of(booking)))
                .thenReturn(List.of(bookingDto));

        var result = bookingService.getAllRides();

        // assert
        assertEquals(1, result.size());
        assertEquals(bookingDto, result.getFirst());
    }

    @Test
    void testGetRideById() {
        // arrange
        GeometryFactory factory = new GeometryFactory();
        var longitude = 10;
        var latitude = 10;
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        var place = new Place(
                1L,
                point
        );
        var user = new User(
                1L,
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                Timestamp.from(Instant.now()),
                "JASO1921AS",
                "JI1O90KA1"
        );
        var car = new Car(
                1L,
                place,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );
        var booking = new Booking(
                1L,
                user,
                car,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MAX),
                "ACTIVE"
        );
        var bookingDto = new BookingDto(
                1L,
                1L,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MAX),
                "ACTIVE"
        );

        // act
        when(bookingRepository.findById(1L))
                .thenReturn(Optional.of(booking));
        when(bookingMapper.entityToDto(booking))
                .thenReturn(bookingDto);

        // assert
        assertEquals(bookingDto, bookingService.getRideById(1L));
    }

    @Test
    void testGetRideByIdNotFound() {
        // act
        when(bookingRepository.findById(1L))
                .thenReturn(Optional.empty());

        // assert
        assertThrows(BookingNotFoundException.class, () -> bookingService.getRideById(1L));
    }

    @Test
    void testGetAllUserRidesByStatus() {
        // arrange
        GeometryFactory factory = new GeometryFactory();
        var longitude = 10;
        var latitude = 10;
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        var place = new Place(
                1L,
                point
        );
        var user = new User(
                1L,
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                Timestamp.from(Instant.now()),
                "JASO1921AS",
                "JI1O90KA1"
        );
        var car = new Car(
                1L,
                place,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );
        var booking = new Booking(
                1L,
                user,
                car,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MAX),
                "ACTIVE"
        );
        var bookingDto = new BookingDto(
                1L,
                1L,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MAX),
                "ACTIVE"
        );

        // act
        when(bookingRepository.findByUserIdAndStatus(1L, "active"))
                .thenReturn(List.of(booking));
        when(bookingMapper.entityToDto(List.of(booking)))
                .thenReturn(List.of(bookingDto));

        var result = bookingService.getAllUserRidesByStatus(1L, "active");

        // assert
        assertEquals(1, result.size());
        assertEquals(bookingDto, result.getFirst());
    }

    @Test
    void testUpdateRideStatus() {
        // arrange
        GeometryFactory factory = new GeometryFactory();
        var longitude = 10;
        var latitude = 10;
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        var place = new Place(
                1L,
                point
        );
        var user = new User(
                1L,
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                Timestamp.from(Instant.now()),
                "JASO1921AS",
                "JI1O90KA1"
        );
        var car = new Car(
                1L,
                place,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );
        var booking = new Booking(
                1L,
                user,
                car,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MAX),
                "ACTIVE"
        );
        var request = new BookingUpdateRequest(
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MIN),
                "ACTIVE"
        );
        var updatedBooking = new Booking(
                1L,
                user,
                car,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MIN),
                "ACTIVE"
        );
        var bookingDto = new BookingDto(
                1L,
                1L,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MIN),
                "ACTIVE"
        );

        // act
        when(bookingRepository.findById(1L))
                .thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking))
                .thenReturn(updatedBooking);
        when(bookingMapper.entityToDto(updatedBooking))
                .thenReturn(bookingDto);

        // assert
        assertEquals(bookingDto, bookingService.updateRideStatus(1L, request));
    }

    @Test
    void testUpdateRideStatusNotFound() {
        // arrange
        var request = new BookingUpdateRequest(
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MIN),
                "ACTIVE"
        );

        // act
        when(bookingRepository.findById(1L))
                .thenReturn(Optional.empty());

        // assert
        assertThrows(BookingNotFoundException.class,
                () -> bookingService.updateRideStatus(1L, request));
    }

    @Test
    void testGetCarRideHistory() {
        // arrange
        GeometryFactory factory = new GeometryFactory();
        var longitude = 10;
        var latitude = 10;
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        var place = new Place(
                1L,
                point
        );
        var user = new User(
                1L,
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                Timestamp.from(Instant.now()),
                "JASO1921AS",
                "JI1O90KA1"
        );
        var car = new Car(
                1L,
                place,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );
        var booking = new Booking(
                1L,
                user,
                car,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MAX),
                "ACTIVE"
        );
        var bookingDto = new BookingDto(
                1L,
                1L,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MAX),
                "ACTIVE"
        );

        // act
        when(bookingRepository.findByCarId(1L))
                .thenReturn(List.of(booking));
        when(bookingMapper.entityToDto(List.of(booking)))
                .thenReturn(List.of(bookingDto));

        var result = bookingService.getCarRideHistory(1L);

        // assert
        assertEquals(1, result.size());
        assertEquals(bookingDto, result.getFirst());
    }

    @Test
    void testGetCarRidesByStatus() {
        // arrange
        GeometryFactory factory = new GeometryFactory();
        var longitude = 10;
        var latitude = 10;
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        var place = new Place(
                1L,
                point
        );
        var user = new User(
                1L,
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                Timestamp.from(Instant.now()),
                "JASO1921AS",
                "JI1O90KA1"
        );
        var car = new Car(
                1L,
                place,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );
        var booking = new Booking(
                1L,
                user,
                car,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MAX),
                "ACTIVE"
        );
        var bookingDto = new BookingDto(
                1L,
                1L,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.MAX),
                "ACTIVE"
        );

        // act
        when(bookingRepository.findByCarIdAndStatus(1L, "active"))
                .thenReturn(List.of(booking));
        when(bookingMapper.entityToDto(List.of(booking)))
                .thenReturn(List.of(bookingDto));

        var result = bookingService.getCarRidesByStatus(1L, "active");

        // assert
        assertEquals(1, result.size());
        assertEquals(bookingDto, result.getFirst());
    }

    @Test
    void testDeleteRide() {
        // act
        when(bookingRepository.existsById(1L))
                .thenReturn(true);

        // assert
        assertDoesNotThrow(() -> bookingService.deleteRide(1L));
    }

    @Test
    void testDeleteRideNotFound() {
        // act
        when(bookingRepository.existsById(1L))
                .thenReturn(false);

        // assert
        assertThrows(BookingNotFoundException.class, () -> bookingService.deleteRide(1L));
    }
}
