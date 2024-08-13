package com.demo.cars.mapper;

import com.demo.cars.dto.UserDto;
import com.demo.cars.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", imports = {java.sql.Timestamp.class, java.time.Instant.class})
public interface UserMapper {
    @Mapping(target = "creationDate", expression = "java(Timestamp.from(Instant.now()))")
    User dtoToEntity(UserDto userDto);

    UserDto entityToDto(User user);

    List<UserDto> entityToDtoList(List<User> userDto);
}
