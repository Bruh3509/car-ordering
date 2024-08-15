package com.demo.cars.service.impl;

import com.demo.cars.dto.CarDto;
import com.demo.cars.exception.CarNotFoundException;
import com.demo.cars.exception.UniqueRecordException;
import com.demo.cars.mapper.CarMapper;
import com.demo.cars.model.car.CarRequest;
import com.demo.cars.repository.CarRepository;
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
    CarMapper carMapper;

    @Override
    public void regCar(CarRequest carRequest) {
        var carDto = carMapper.requestToDto(carRequest);

        checkUniqueness(carDto.getPlateNumber());

        carRepository.save(carMapper.dtoToEntity(carDto));
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
        var carDto = carMapper.requestToDto(carRequest);

        checkUniquenessAndIdNot(id, carDto.getPlateNumber());

        car.setCarClass(carDto.getCarClass());
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setNumberOfSeats(carDto.getNumberOfSeats());
        car.setYearOfProduction(carDto.getYearOfProduction());
        car.setPlateNumber(carDto.getPlateNumber());
        car.setIsAvailable(carDto.getIsAvailable());
        car.setDailyFee(carDto.getDailyFee());

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
