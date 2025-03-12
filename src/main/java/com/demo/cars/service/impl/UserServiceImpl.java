package com.demo.cars.service.impl;

import com.demo.cars.dto.UserDto;
import com.demo.cars.exception.UniqueRecordException;
import com.demo.cars.exception.UserNotFoundException;
import com.demo.cars.mapper.UserMapper;
import com.demo.cars.model.user.UserRequest;
import com.demo.cars.repository.UserRepository;
import com.demo.cars.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.demo.cars.util.PropertyUtil.DR_LICENSE_EXC;
import static com.demo.cars.util.PropertyUtil.EMAIL_EXC;
import static com.demo.cars.util.PropertyUtil.PASSPORT_EXC;
import static com.demo.cars.util.PropertyUtil.PHONE_EXC;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public UserDto regUser(UserRequest userRequest) {
        var userDto = userMapper.requestToDto(userRequest);

        checkUniqueness(userDto);

        return userMapper.entityToDto(
                userRepository.save(
                        userMapper.dtoToEntity(userDto)
                )
        );
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.entityToDto(userRepository.findAll());
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::entityToDto)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDto updateUser(Long id, UserRequest userRequest) {
        var user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        var userDto = userMapper.requestToDto(userRequest);

        checkUniquenessAndIdNot(userDto, id);

        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassportId(userDto.getPassportId());
        user.setDrivingLicenseId(userDto.getDrivingLicenseId());

        return userMapper.entityToDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException();

        userRepository.deleteById(id);
    }

    private void checkUniquenessAndIdNot(UserDto userDto, Long id) {
        if (userRepository.existsByEmailAndIdNot(userDto.getEmail(), id))
            throw new UniqueRecordException(EMAIL_EXC);
        else if (userRepository.existsByPhoneNumberAndIdNot(userDto.getPhoneNumber(), id))
            throw new UniqueRecordException(PHONE_EXC);
        else if (userRepository.existsByPassportIdAndIdNot(userDto.getPassportId(), id))
            throw new UniqueRecordException(PASSPORT_EXC);
        else if (userRepository.existsByDrivingLicenseIdAndIdNot(userDto.getDrivingLicenseId(), id))
            throw new UniqueRecordException(DR_LICENSE_EXC);
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
