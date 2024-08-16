package com.demo.cars.service.impl;

import com.demo.cars.mapper.PlacesMapper;
import com.demo.cars.repository.PlacesRepository;
import com.demo.cars.service.PlacesService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlacesServiceImpl implements PlacesService {
    PlacesRepository repository;
    PlacesMapper mapper;
}
