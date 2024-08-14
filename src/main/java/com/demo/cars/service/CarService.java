package com.demo.cars.service;

import com.demo.cars.dto.CarDto;

import java.util.List;

public interface CarService {
    void regCar(CarDto carDto);

    List<CarDto> getAllCars();

    CarDto getCarById(Long id);

    CarDto updateCar(Long id, CarDto carDto);

    void deleteCar(Long id);
}
