package com.demo.cars.mapper;

import com.demo.cars.dto.PlaceDto;
import com.demo.cars.entity.Place;
import com.demo.cars.model.places.PlaceRequest;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static com.demo.cars.util.PropertyUtil.SRID;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    @Mapping(target = "longitude", source = "location.x")
    @Mapping(target = "latitude", source = "location.y")
    PlaceDto entityToDto(Place entity);

    @Mapping(target = "longitude", source = "location.x")
    @Mapping(target = "latitude", source = "location.y")
    List<PlaceDto> entityToDto(List<Place> places);

    @Mapping(target = "location", expression = "java(createPoint(dto.getLongitude(), dto.getLatitude()))")
    Place dtoToEntity(PlaceDto dto);

    @Mapping(target = "location", expression = "java(createPoint(request.longitude(), request.latitude()))")
    Place requestToEntity(PlaceRequest request);

    default Point createPoint(double longitude, double latitude) {
        GeometryFactory factory = new GeometryFactory();
        var point = factory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        return point;
    }
}
