package com.demo.cars.database.service;

import com.demo.cars.database.exception.UniqueRecordException;
import com.demo.cars.dto.UserDto;

import java.util.List;

// User(entity) or UserDto
public interface UserService {
    UserDto regUser(UserDto userDto) throws UniqueRecordException;
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto updateUser(UserDto userDto);
    void deleteUser(Long id);
}
