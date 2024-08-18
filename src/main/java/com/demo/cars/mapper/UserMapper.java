package com.demo.cars.mapper;

import com.demo.cars.dto.UserDto;
import com.demo.cars.entity.User;
import com.demo.cars.model.user.UserRequest;
import com.demo.cars.model.user.UserResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", imports = {java.sql.Timestamp.class, java.time.Instant.class})
public interface UserMapper {
    @Mapping(target = "creationDate", expression = "java(Timestamp.from(Instant.now()))")
    User dtoToEntity(UserDto userDto);

    UserDto entityToDto(User user);

    List<UserDto> entityToDto(List<User> userDto);

    UserDto requestToDto(UserRequest userRequest);

    UserResponse dtoToResponse(UserDto userDto);

    List<UserResponse> dtoToResponse(List<UserDto> userDto);

}
