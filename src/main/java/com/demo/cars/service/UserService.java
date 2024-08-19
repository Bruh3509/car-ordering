package com.demo.cars.service;

import com.demo.cars.dto.UserDto;
import com.demo.cars.model.user.UserRequest;

import java.util.List;

public interface UserService {
    void regUser(UserRequest userDto);

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto updateUser(Long id, UserRequest userDto);

    void deleteUser(Long id);
}
