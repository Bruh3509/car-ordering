package com.demo.cars.controller;

import com.demo.cars.dto.CarDto;
import com.demo.cars.mapper.CarMapper;
import com.demo.cars.model.car.CarRequest;
import com.demo.cars.service.CarService;
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
@RequestMapping("/car")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarController {
    CarService carService;
    CarMapper carMapper;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CarDto> getCarInfo(@PathVariable Long id) {
        return new ResponseEntity<>(carService.getCarById(id), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<CarDto>> getAllCars() {
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<CarDto> regNewCar(@RequestBody CarRequest request) {
        return new ResponseEntity<>(carService.regCar(request), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CarDto> updateCarInfo(
            @PathVariable Long id,
            @RequestBody CarRequest request
    ) {
        var response = carService.updateCar(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
