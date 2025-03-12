package com.demo.cars.service;

import com.demo.cars.dto.PlaceDto;
import com.demo.cars.model.places.PlaceRequest;

import java.util.List;

public interface PlaceService {
    PlaceDto addPlace(PlaceRequest placeRequest);

    List<PlaceDto> getAllPlaces();

    PlaceDto getPlaceById(long id);

    PlaceDto updatePlace(long id, PlaceRequest placeRequest);

    void deletePlace(long id);
}
