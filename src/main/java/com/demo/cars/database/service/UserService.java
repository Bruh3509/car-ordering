package com.demo.cars.database.service;

import com.demo.cars.database.fields.user.UserColumn;
import com.demo.cars.dto.UserDto;

public interface UserService {
    void regUser(UserDto userDto);
    void getAllUsers();
    void getUserById();
    <T> void updateUser(Long id, UserColumn column, T newValue);
    void deleteUser(Long id);
}
