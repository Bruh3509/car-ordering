package com.demo.cars.service.impl;

import com.demo.cars.dto.CarDto;
import com.demo.cars.exception.CarNotFoundException;
import com.demo.cars.exception.PlaceNotFoundException;
import com.demo.cars.exception.UniqueRecordException;
import com.demo.cars.mapper.CarBuilder;
import com.demo.cars.mapper.CarMapper;
import com.demo.cars.model.car.CarRequest;
import com.demo.cars.repository.CarRepository;
import com.demo.cars.repository.PlaceRepository;
import com.demo.cars.service.CarService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.demo.cars.util.PropertyUtil.PLATE_NUM_EXC;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarServiceImpl implements CarService {
    CarRepository carRepository;
    PlaceRepository placeRepository;
    CarMapper carMapper;

    @Override
    public CarDto regCar(CarRequest carRequest) {
        checkUniqueness(carRequest.plateNumber());

        var place = placeRepository.findById(carRequest.branchId())
                .orElseThrow(PlaceNotFoundException::new);

        var car = CarBuilder.buildEntity(place, carRequest);

        return carMapper.entityToDto(carRepository.save(car));
    }

    @Override
    public List<CarDto> getAllCars() {
        return carMapper.entityToDto(carRepository.findAll());
    }

    @Override
    public CarDto getCarById(Long id) {
        return carRepository.findById(id)
                .map(carMapper::entityToDto)
                .orElseThrow(CarNotFoundException::new);
    }

    @Override
    public CarDto updateCar(Long id, CarRequest carRequest) {
        var car = carRepository.findById(id)
                .orElseThrow(CarNotFoundException::new);

        checkUniquenessAndIdNot(id, carRequest.plateNumber());

        car.setCarClass(carRequest.carClass());
        car.setBrand(carRequest.brand());
        car.setModel(carRequest.model());
        car.setNumberOfSeats(carRequest.numberOfSeats());
        car.setYearOfProduction(carRequest.yearOfProduction());
        car.setPlateNumber(carRequest.plateNumber());
        car.setIsAvailable(carRequest.isAvailable());
        car.setMinuteFee(carRequest.minuteFee());

        return carMapper.entityToDto(carRepository.save(car));
    }

    @Override
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id))
            throw new CarNotFoundException();

        carRepository.deleteById(id);
    }

    private void checkUniqueness(String plateNumber) {
        if (carRepository.existsByPlateNumber(plateNumber))
            throw new UniqueRecordException(PLATE_NUM_EXC);
    }

    private void checkUniquenessAndIdNot(Long id, String plateNumber) {
        if (carRepository.existsByPlateNumberAndIdNot(plateNumber, id))
            throw new UniqueRecordException(PLATE_NUM_EXC);
    }
}
