package com.demo.cars.service;

import com.demo.cars.dto.UserDto;

import java.util.List;

public interface UserService {
    void regUser(UserDto userDto);

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);
}
