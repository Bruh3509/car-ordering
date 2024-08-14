package com.demo.cars.controller;

import com.demo.cars.mapper.CarMapper;
import com.demo.cars.model.car.CarRequest;
import com.demo.cars.model.car.CarResponse;
import com.demo.cars.service.CarService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarController {
    CarService carService;
    CarMapper carMapper;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CarResponse> getCarInfo(@PathVariable Long id) {
        return new ResponseEntity<>(carMapper.dtoToResponse(carService.getCarById(id)), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<CarResponse>> getAllCars() {
        return new ResponseEntity<>(carMapper.dtoToResponse(carService.getAllCars()), HttpStatus.OK);
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<Void> regNewCar(@RequestBody CarRequest request) {
        carService.regCar(carMapper.requestToDto(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CarResponse> updateCarInfo(
            @PathVariable Long id,
            @RequestBody CarRequest request
    ) {
        var response = carService.updateCar(id, carMapper.requestToDto(request));
        return new ResponseEntity<>(carMapper.dtoToResponse(response), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
