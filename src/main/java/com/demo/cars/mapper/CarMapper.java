package com.demo.cars.mapper;

import com.demo.cars.dto.CarDto;
import com.demo.cars.entity.Car;
import com.demo.cars.model.car.CarRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {
    Car dtoToEntity(CarDto carDto);

    CarDto entityToDto(Car car);

    List<CarDto> entityToDto(List<Car> cars);

    CarDto requestToDto(CarRequest request);
}