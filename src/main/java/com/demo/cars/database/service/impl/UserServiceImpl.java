package com.demo.cars.database.service.impl;

import com.demo.cars.database.exception.UniqueRecordException;
import com.demo.cars.database.repository.UserRepository;
import com.demo.cars.database.service.UserService;
import com.demo.cars.dto.UserDto;
import com.demo.cars.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper mapper;

    @Override
    public UserDto regUser(UserDto userDto) throws UniqueRecordException {
        var user = mapper.dtoToEntity(userDto);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueRecordException(e.getMessage()); // custom exception
        }

        return userDto; // договорились возвращать, но есть ли смысл тут, если и так передаем что возвращаем
    }

    @Override
    public List<UserDto> getAllUsers() {
        return mapper.entityToDtoList(userRepository.findAll());
    }

    @Override
    public UserDto getUserById(Long id) {
        var optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            return mapper.entityToDto(optUser.get());
        } else {
            return new UserDto();
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
