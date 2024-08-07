package com.demo.cars.mapper;

import com.demo.cars.database.entity.User;
import com.demo.cars.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", imports = {java.sql.Timestamp.class, java.time.Instant.class})
public interface UserMapper {
    @Mapping(target = "creationDate", expression = "java(Timestamp.from(Instant.now()))")
    User dtoToEntity(UserDto userDto);

    UserDto entityToDto(User user);

    List<UserDto> entityToDtoList(List<User> userDto);

    default void updateEntityFromDto(UserDto userDto, @MappingTarget User user) {
        if (userDto == null) {
            return;
        }

        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassportId(userDto.getPassportId());
        user.setDrivingLicenseId(userDto.getDrivingLicenseId());
    }
}
