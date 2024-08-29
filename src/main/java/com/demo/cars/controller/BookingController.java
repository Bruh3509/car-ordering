package com.demo.cars.controller;

import com.demo.cars.dto.BookingDto;
import com.demo.cars.model.booking.BookingPostRequest;
import com.demo.cars.model.booking.BookingUpdateRequest;
import com.demo.cars.service.BookingService;
import com.demo.cars.util.enums.Status;
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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {
    BookingService service;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<BookingDto>> getAllBookRecords() {
        return new ResponseEntity<>(service.getAllRides(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<BookingDto> getBookRecordById(@PathVariable long id) {
        return new ResponseEntity<>(service.getRideById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/user-id/{userId}", produces = "application/json")
    public ResponseEntity<List<BookingDto>> getBooksByUserIdAndStatus(
            @PathVariable long userId,
            @RequestParam(name = "status", required = false, defaultValue = "ACTIVE") String status
    ) {
        if (status.isEmpty())
            return new ResponseEntity<>(service.getAllUserRides(userId), HttpStatus.OK);

        return new ResponseEntity<>(service.getAllUserRidesByStatus(userId, status), HttpStatus.OK);
    }

    @GetMapping(value = "/car-id/{carId}", produces = "application/json")
    public ResponseEntity<List<BookingDto>> getBooksByCarIdAndStatus(
            @PathVariable long carId,
            @RequestParam(name = "status", required = false, defaultValue = "FINISHED") String status
    ) {
        if (status.isEmpty())
            return new ResponseEntity<>(service.getCarRideHistory(carId), HttpStatus.OK);

        return new ResponseEntity<>(service.getCarRidesByStatus(carId, status), HttpStatus.OK);
    }

    @PostMapping(value = "/register", produces = "application/json", consumes = "application/json")
    public ResponseEntity<BookingDto> registerNewBook(@RequestBody BookingPostRequest request) {
        return new ResponseEntity<>(service.addNewRide(request), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/finish/{id}", produces = "application/json")
    public ResponseEntity<BookingDto> updateBookInfo(
            @PathVariable long id
    ) {
        return new ResponseEntity<>(service.updateRideStatus(
                id,
                new BookingUpdateRequest(Timestamp.from(Instant.now()), Status.COMPLETE.name())
        ),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookRecord(@PathVariable long id) {
        service.deleteRide(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
