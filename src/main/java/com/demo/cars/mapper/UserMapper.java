package com.demo.cars.mapper;

import com.demo.cars.database.entity.User;
import com.demo.cars.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDtoToUserEntity(UserDto userDto);
    UserDto userEntityToUserDto(User user);
}
