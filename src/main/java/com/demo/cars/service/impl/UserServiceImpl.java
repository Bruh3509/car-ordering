package com.demo.cars.service.impl;

import com.demo.cars.dto.UserDto;
import com.demo.cars.exception.UniqueRecordException;
import com.demo.cars.exception.UserNotFoundException;
import com.demo.cars.mapper.UserMapper;
import com.demo.cars.repository.UserRepository;
import com.demo.cars.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.demo.cars.utility.PropertyUtil.*;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper mapper;

    @Override
    public void regUser(UserDto userDto) {
        checkUniqueness(userDto);

        userRepository.save(mapper.dtoToEntity(userDto));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return mapper.entityToDtoList(userRepository.findAll());
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException();

        checkUniqueness(userDto);

        var user = mapper.dtoToEntity(userDto);
        return mapper.entityToDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException();

        userRepository.deleteById(id);
    }

    private void checkUniqueness(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail()))
            throw new UniqueRecordException(EMAIL_EXC);
        else if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber()))
            throw new UniqueRecordException(PHONE_EXC);
        else if (userRepository.existsByPassportId(userDto.getPassportId()))
            throw new UniqueRecordException(PASSPORT_EXC);
        else if (userRepository.existsByDrivingLicenseId(userDto.getDrivingLicenseId()))
            throw new UniqueRecordException(DR_LICENSE_EXC);
    }
}
