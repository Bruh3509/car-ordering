package com.demo.cars.database.service.impl;

import com.demo.cars.database.exception.UniqueRecordException;
import com.demo.cars.database.exception.UserNotFoundException;
import com.demo.cars.database.repository.UserRepository;
import com.demo.cars.database.service.UserService;
import com.demo.cars.dto.UserDto;
import com.demo.cars.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.demo.cars.database.exception.UniqueRecordException.*;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper mapper;

    @Override
    public void regUser(UserDto userDto) throws UniqueRecordException {
        // validation
        checkUniqueness(userDto);

        userRepository.save(mapper.dtoToEntity(userDto));
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
            return new UserDto(); // если не найден user,
            // то наверное лучше выкинуть исключение, чем вернуть пустое dto ?
        }
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) throws UserNotFoundException, UniqueRecordException {
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        // validation
        checkUniqueness(userDto);

        var user = mapper.dtoToEntity(userDto);
        return mapper.entityToDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new); // а нужно ли исключение?
        userRepository.deleteById(id);
    }

    private void checkUniqueness(UserDto userDto) throws UniqueRecordException {
        if (userRepository.existsByEmail(userDto.getEmail())) throw new UniqueRecordException(EMAIL_EXC);
        else if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber()))
            throw new UniqueRecordException(PHONE_EXC);
        else if (userRepository.existsByPassportId(userDto.getPassportId()))
            throw new UniqueRecordException(PASSPORT_EXC);
        else if (userRepository.existsByDrivingLicenseId(userDto.getDrivingLicenseId()))
            throw new UniqueRecordException(DR_LICENSE_EXC);
    }
}
