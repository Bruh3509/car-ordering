package com.demo.cars.controller;

import com.demo.cars.dto.BookingDto;
import com.demo.cars.model.booking.BookingPostRequest;
import com.demo.cars.model.booking.BookingUpdateRequest;
import com.demo.cars.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/booking")
@Tag(name = "booking-controller", description = "managing booking logic")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {
    BookingService service;

    @GetMapping(produces = "application/json")
    @Operation(summary = "get all records", description = "lists whole booking history")
    public ResponseEntity<List<BookingDto>> getAllBookRecords() {
        return new ResponseEntity<>(service.getAllRides(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "get by id", description = "gets booking record by its id")
    public ResponseEntity<BookingDto> getBookRecordById(
            @PathVariable long id
    ) {
        return new ResponseEntity<>(service.getRideById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/user-id/{userId}", produces = "application/json")
    @Operation(summary = "get by user and status",
            description = "lists all booking records which belongs to specified user with specified status")
    public ResponseEntity<List<BookingDto>> getBooksByUserIdAndStatus(
            @PathVariable
            long userId,
            @RequestParam(name = "status", required = false, defaultValue = "ACTIVE")
            @Parameter(name = "status", description = "status of the ride/reservation")
            String status
    ) {
        if (status.isEmpty())
            return new ResponseEntity<>(service.getAllUserRides(userId), HttpStatus.OK);

        return new ResponseEntity<>(service.getAllUserRidesByStatus(userId, status), HttpStatus.OK);
    }

    @GetMapping(value = "/car-id/{carId}", produces = "application/json")
    @Operation(summary = "get by car and status",
            description = "lists all booking records with specified car and status")
    public ResponseEntity<List<BookingDto>> getBooksByCarIdAndStatus(
            @PathVariable
            long carId,
            @RequestParam(name = "status", required = false, defaultValue = "FINISHED")
            @Parameter(name = "status", description = "status of the ride/reservation")
            String status
    ) {
        if (status.isEmpty())
            return new ResponseEntity<>(service.getCarRideHistory(carId), HttpStatus.OK);

        return new ResponseEntity<>(service.getCarRidesByStatus(carId, status), HttpStatus.OK);
    }

    @PostMapping(value = "/register", produces = "application/json", consumes = "application/json")
    @Operation(summary = "add new",
            description = "register new booking record")
    public ResponseEntity<BookingDto> registerNewBook(
            @RequestBody
            @Parameter(name = "new reservation post request", required = true)
            BookingPostRequest request
    ) {
        return new ResponseEntity<>(service.addNewRide(request), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/update/{id}", produces = "application/json", consumes = "application/json")
    @Operation(summary = "update info",
            description = "update booking record info by id")
    public ResponseEntity<BookingDto> updateBookInfo(
            @PathVariable
            long id,
            @RequestBody
            @Parameter(name = "update reservation info request", required = true)
            BookingUpdateRequest request
    ) {
        return new ResponseEntity<>(service.updateRideStatus(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete", description = "delete booking record")
    public ResponseEntity<Void> deleteBookRecord(
            @PathVariable long id
    ) {
        service.deleteRide(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
