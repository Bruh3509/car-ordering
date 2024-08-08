package com.demo.cars.database.service;

import com.demo.cars.database.exception.UniqueRecordException;
import com.demo.cars.database.exception.UserNotFoundException;
import com.demo.cars.dto.UserDto;

import java.util.List;

// User(entity) or UserDto
public interface UserService {
    // договорились возвращать dto, но есть ли смысл тут, если и так передаем что возвращаем
    void regUser(UserDto userDto) throws UniqueRecordException;
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto updateUser(Long id, UserDto userDto) throws UserNotFoundException, UniqueRecordException;
    void deleteUser(Long id) throws UserNotFoundException;
}
