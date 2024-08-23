package com.demo.cars.mapper;

import com.demo.cars.dto.CarDto;
import com.demo.cars.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = PlaceMapper.class)
public interface CarMapper {
    @Mapping(target = "placeDto", source = "place")
    CarDto entityToDto(Car car);

    @Mapping(target = "placeDto", source = "place")
    List<CarDto> entityToDto(List<Car> cars);
}
