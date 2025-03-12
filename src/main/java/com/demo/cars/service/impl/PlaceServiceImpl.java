package com.demo.cars.service.impl;

import com.demo.cars.dto.PlaceDto;
import com.demo.cars.exception.PlaceNotFoundException;
import com.demo.cars.mapper.PlaceMapper;
import com.demo.cars.model.places.PlaceRequest;
import com.demo.cars.repository.PlaceRepository;
import com.demo.cars.service.PlaceService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaceServiceImpl implements PlaceService {
    PlaceRepository repository;
    PlaceMapper mapper;

    @Override
    public PlaceDto addPlace(PlaceRequest placeRequest) {
        return mapper.entityToDto(
                repository.save(
                        mapper.requestToEntity(placeRequest)
                )
        );
    }

    @Override
    public List<PlaceDto> getAllPlaces() {
        return mapper.entityToDto(
                repository.findAll()
        );
    }

    @Override
    public PlaceDto getPlaceById(long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow(PlaceNotFoundException::new);
    }

    @Override
    public PlaceDto updatePlace(long id, PlaceRequest placeRequest) {
        var place = repository.findById(id)
                .orElseThrow(PlaceNotFoundException::new);

        place.setLocation(mapper.createPoint(placeRequest.longitude(), placeRequest.latitude()));

        return mapper.entityToDto(repository.save(place));
    }

    @Override
    public void deletePlace(long id) {
        if (!repository.existsById(id))
            throw new PlaceNotFoundException();

        repository.deleteById(id);
    }
}
