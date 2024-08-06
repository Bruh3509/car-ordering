package com.demo.cars.database.service;

import com.demo.cars.database.fields.user.UserColumn;
import com.demo.cars.database.repository.UserRepository;
import com.demo.cars.dto.UserDto;
import com.demo.cars.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceJpa implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Autowired
    public UserServiceJpa(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void regUser(UserDto userDto) {
        var user = mapper.userDtoToUserEntity(userDto);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional
    public void getAllUsers() {

    }

    @Override
    @Transactional
    public void getUserById() {

    }

    @Override
    @Transactional
    public <T> void updateUser(Long id, UserColumn column, T newValue) {

    }

    @Override
    @Transactional
    public void deleteUser(Long id) {

    }
}
