package com.demo.cars.controller;

import com.demo.cars.dto.PlaceDto;
import com.demo.cars.model.places.PlaceRequest;
import com.demo.cars.service.PlaceService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaceController {
    PlaceService service;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<PlaceDto>> getAllPlaces() {
        return new ResponseEntity<>(service.getAllPlaces(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<PlaceDto> getPlaceById(@PathVariable long id) {
        return new ResponseEntity<>(service.getPlaceById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/place", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PlaceDto> addNewPlace(@RequestBody PlaceRequest request) {
        return new ResponseEntity<>(service.addPlace(request), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PlaceDto> updatePlaceInfo(
            @PathVariable long id,
            @RequestBody PlaceRequest request
    ) {
        return new ResponseEntity<>(service.updatePlace(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable long id) {
        service.deletePlace(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
