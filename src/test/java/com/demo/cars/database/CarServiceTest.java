package com.demo.cars.database;

import com.demo.cars.dto.CarDto;
import com.demo.cars.dto.PlaceDto;
import com.demo.cars.entity.Car;
import com.demo.cars.entity.Place;
import com.demo.cars.exception.CarNotFoundException;
import com.demo.cars.exception.UniqueRecordException;
import com.demo.cars.mapper.CarMapperImpl;
import com.demo.cars.model.car.CarRequest;
import com.demo.cars.repository.CarRepository;
import com.demo.cars.repository.PlaceRepository;
import com.demo.cars.service.impl.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import static com.demo.cars.util.PropertyUtil.SRID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private PlaceRepository placeRepository;
    @Mock
    private CarMapperImpl carMapper;
    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    void resetMockRules() {
        Mockito.reset(carRepository);
        Mockito.reset(carMapper);
    }

    @Test
    void testFindAll() {
        // arrange
        GeometryFactory factory = new GeometryFactory();
        var longitude = 10;
        var latitude = 10;
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        var place = new Place(
                1L,
                point
        );
        var placeDto = new PlaceDto(
                longitude,
                latitude
        );
        var carEntity = new Car(
                1L,
                place,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );
        var carDto = new CarDto(
                placeDto,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );

        // act
        when(carRepository.findAll())
                .thenReturn(List.of(carEntity));
        when(carMapper.entityToDto(List.of(carEntity)))
                .thenReturn(List.of(carDto));
        var car = carService.getAllCars();

        // assert
        assertEquals(1, car.size());
        assertEquals("M5 F90", car.getFirst().getModel());
    }

    @Test
    void testRegCar() {
        // arrange
        GeometryFactory factory = new GeometryFactory();
        var longitude = 10;
        var latitude = 10;
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        var place = new Place(
                1L,
                point
        );
        var placeDto = new PlaceDto(
                longitude,
                latitude
        );
        var car = new Car(
                1L,
                place,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );
        var carDto = new CarDto(
                placeDto,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );
        var carRequest = new CarRequest(
                1L,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );

        // act
        when(placeRepository.findById(1L))
                .thenReturn(Optional.of(place));
        when(carRepository.existsByPlateNumber(carDto.getPlateNumber()))
                .thenReturn(false);
        when(carRepository.save(car))
                .thenReturn(car);
        when(carMapper.entityToDto(car))
                .thenReturn(carDto);

        // assert
        assertEquals(carDto, carService.regCar(carRequest));
    }

    @Test
    void testRegCarExc() {
        // arrange
        GeometryFactory factory = new GeometryFactory();
        var longitude = 10;
        var latitude = 10;
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        var placeDto = new PlaceDto(
                longitude,
                latitude
        );
        var carDto = new CarDto(
                placeDto,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );
        var carRequest = new CarRequest(
                1L,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );

        // act
        when(carRepository.existsByPlateNumber(carDto.getPlateNumber()))
                .thenReturn(true);

        // assert
        assertThrows(UniqueRecordException.class, () -> carService.regCar(carRequest));
    }

    @Test
    void testGetById() {
        // arrange
        GeometryFactory factory = new GeometryFactory();
        var longitude = 10;
        var latitude = 10;
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        var place = new Place(
                1L,
                point
        );
        var placeDto = new PlaceDto(
                longitude,
                latitude
        );
        var carEntity = new Car(
                1L,
                place,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );
        var carDto = new CarDto(
                placeDto,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );

        // act
        when(carRepository.findById(carEntity.getId()))
                .thenReturn(Optional.of(carEntity));
        when(carMapper.entityToDto(carEntity))
                .thenReturn(carDto);
        var car = carService.getCarById(carEntity.getId());

        // assert
        assertEquals(carDto, car);
    }

    @Test
    void testGetByIdExc() {
        // arrange
        GeometryFactory factory = new GeometryFactory();
        var longitude = 10;
        var latitude = 10;
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        var place = new Place(
                1L,
                point
        );
        var carEntity = new Car(
                1L,
                place,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );

        // act
        when(carRepository.findById(carEntity.getId()))
                .thenReturn(Optional.empty());

        // assert
        assertThrows(CarNotFoundException.class, () -> carService.getCarById(1L));
    }

    @Test
    void testUpdateCar() {
        // arrange
        GeometryFactory factory = new GeometryFactory();
        var longitude = 10;
        var latitude = 10;
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        var place = new Place(
                1L,
                point
        );
        var placeDto = new PlaceDto(
                longitude,
                latitude
        );
        var carEntity = new Car(
                1L,
                place,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );

        var carDto = new CarDto(
                placeDto,
                "Universal",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );
        var carRequest = new CarRequest(
                1L,
                "Universal",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );

        // act
        when(carRepository.findById(carEntity.getId()))
                .thenReturn(Optional.of(carEntity));
        when(carRepository.existsByPlateNumberAndIdNot(carDto.getPlateNumber(), carEntity.getId()))
                .thenReturn(false);
        when(carRepository.save(carEntity))
                .thenReturn(carEntity);
        when(carMapper.entityToDto(carEntity))
                .thenReturn(carDto);

        var car = carService.updateCar(carEntity.getId(), carRequest);

        // assert
        assertEquals(carDto, car);
    }

    @Test
    void testUpdateCarNotFoundExc() {
        // arrange
        GeometryFactory factory = new GeometryFactory();
        var longitude = 10;
        var latitude = 10;
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        var place = new Place(
                1L,
                point
        );
        var carEntity = new Car(
                1L,
                place,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );
        var carRequest = new CarRequest(
                1L,
                "Universal",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );

        // act
        when(carRepository.findById(carEntity.getId()))
                .thenReturn(Optional.empty());

        // assert
        assertThrows(CarNotFoundException.class, () -> carService.updateCar(1L, carRequest));
    }

    @Test
    void testUpdateCarUniqueExc() {
        // arrange
        GeometryFactory factory = new GeometryFactory();
        var longitude = 10;
        var latitude = 10;
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        var place = new Place(
                1L,
                point
        );
        var placeDto = new PlaceDto(
                longitude,
                latitude
        );
        var carEntity = new Car(
                1L,
                place,
                "Sedan",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );

        var carDto = new CarDto(
                placeDto,
                "Universal",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );
        var carRequest = new CarRequest(
                1L,
                "Universal",
                "BMW",
                "M5 F90",
                (byte) 4,
                Year.of(2020),
                "1234 AM-7",
                true,
                100
        );

        // act
        when(carRepository.findById(carEntity.getId()))
                .thenReturn(Optional.of(carEntity));
        when(carRepository.existsByPlateNumberAndIdNot(carDto.getPlateNumber(), carEntity.getId()))
                .thenReturn(true);

        // assert
        assertThrows(UniqueRecordException.class, () -> carService.updateCar(1L, carRequest));
    }
}
