package com.demo.cars.mapper;

import com.demo.cars.dto.PlacesDto;
import com.demo.cars.entity.Places;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlacesMapper {
    @Mapping(target = "longitude", source = "location.x")
    @Mapping(target = "latitude", source = "location.y")
    PlacesDto entityToDto(Places entity);

    @Mapping(target = "location", expression = "java(createPoint(dto.getLongitude(), dto.getLatitude()))")
    Places dtoToEntity(PlacesDto dto);

    default Point createPoint(double longitude, double latitude) {
        GeometryFactory factory = new GeometryFactory();
        return factory.createPoint(new Coordinate(longitude, latitude));
    }
}
