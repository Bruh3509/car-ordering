package com.demo.cars.service;

import com.demo.cars.dto.CarDto;
import com.demo.cars.model.car.CarRequest;

import java.util.List;

public interface CarService {
    void regCar(CarRequest carRequest);

    List<CarDto> getAllCars();

    CarDto getCarById(Long id);

    CarDto updateCar(Long id, CarRequest carDto);

    void deleteCar(Long id);
}
