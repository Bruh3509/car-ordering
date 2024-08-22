package com.demo.cars.database;

import com.demo.cars.dto.PlaceDto;
import com.demo.cars.entity.Place;
import com.demo.cars.exception.PlaceNotFoundException;
import com.demo.cars.mapper.PlaceMapperImpl;
import com.demo.cars.model.places.PlaceRequest;
import com.demo.cars.repository.PlaceRepository;
import com.demo.cars.service.impl.PlaceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.demo.cars.util.PropertyUtil.SRID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {
    @Mock
    PlaceRepository repository;
    @Mock
    PlaceMapperImpl mapper;
    @InjectMocks
    PlaceServiceImpl service;

    @BeforeEach
    void resetMockRules() {
        Mockito.reset(repository);
        Mockito.reset(mapper);
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
        var placeDto = new PlaceDto(point.getX(), point.getY());

        // act
        when(repository.findAll())
                .thenReturn(List.of(place));
        when(mapper.entityToDto(List.of(place)))
                .thenReturn(List.of(placeDto));

        // assert
        assertEquals(List.of(placeDto), service.getAllPlaces());
    }

    @Test
    void testAddPlace() {
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
        var placeDto = new PlaceDto(point.getX(), point.getY());
        var request = new PlaceRequest(longitude, latitude);

        // act
        when(repository.save(place))
                .thenReturn(place);
        when(mapper.requestToEntity(request))
                .thenReturn(place);
        when(mapper.entityToDto(place))
                .thenReturn(placeDto);
        var res = service.addPlace(request);

        // assert
        assertEquals(placeDto, res);
    }

    @Test
    void testGetPlaceById() {
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
        var placeDto = new PlaceDto(point.getX(), point.getY());

        // act
        when(repository.findById(1L))
                .thenReturn(Optional.of(place));
        when(mapper.entityToDto(place))
                .thenReturn(placeDto);
        var res = service.getPlaceById(1L);

        // assert
        assertEquals(placeDto, res);
    }

    @Test
    void testGetPlaceByIdExc() {
        // act
        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        // assert
        assertThrows(PlaceNotFoundException.class, () -> service.getPlaceById(1L));
    }

    @Test
    void testUpdatePlace() {
        // arrange
        GeometryFactory factory = new GeometryFactory();
        var longitude = 10;
        var latitude = 10;
        var longitudeUpd = 20;
        var latitudeUpd = 30;
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        var pointUpd = factory.createPoint(new Coordinate(longitudeUpd, latitudeUpd));
        point.setSRID(SRID);
        var place = new Place(
                1L,
                point
        );
        var placeDto = new PlaceDto(point.getX(), point.getY());
        var request = new PlaceRequest(longitudeUpd, latitudeUpd);

        // act
        when(repository.findById(1L))
                .thenReturn(Optional.of(place));
        when(mapper.createPoint(longitudeUpd, latitudeUpd))
                .thenReturn(pointUpd);
        when(repository.save(place))
                .thenReturn(place);
        when(mapper.entityToDto(place))
                .thenReturn(placeDto);
        var res = service.updatePlace(1L, request);

        // assert
        assertEquals(placeDto, res);
    }

    @Test
    void testUpdatePlaceExc() {
        // arrange
        var request = new PlaceRequest(
                10,
                10
        );
        // act
        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        // assert
        assertThrows(PlaceNotFoundException.class, () -> service.updatePlace(1L, request));
    }

    @Test
    void testDeletePlace() {
        // act
        when(repository.existsById(1L))
                .thenReturn(true);

        // assert
        assertDoesNotThrow(() -> service.deletePlace(1L));
    }

    @Test
    void testDeletePlaceExc() {
        // act
        when(repository.existsById(1L))
                .thenReturn(false);

        // assert
        assertThrows(PlaceNotFoundException.class, () -> service.deletePlace(1L));
    }
}
