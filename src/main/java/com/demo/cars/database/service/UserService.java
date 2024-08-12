package com.demo.cars.database.service;

import com.demo.cars.dto.UserDto;

import java.util.List;

public interface UserService {
    // договорились возвращать dto, но есть ли смысл тут, если и так передаем что возвращаем
    void regUser(UserDto userDto);
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
}
