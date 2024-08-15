package com.demo.cars.database;

import com.demo.cars.dto.UserDto;
import com.demo.cars.entity.User;
import com.demo.cars.exception.UniqueRecordException;
import com.demo.cars.exception.UserNotFoundException;
import com.demo.cars.mapper.UserMapperImpl;
import com.demo.cars.model.UserRequest;
import com.demo.cars.repository.UserRepository;
import com.demo.cars.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.demo.cars.util.PropertyUtil.DR_LICENSE_EXC;
import static com.demo.cars.util.PropertyUtil.EMAIL_EXC;
import static com.demo.cars.util.PropertyUtil.PASSPORT_EXC;
import static com.demo.cars.util.PropertyUtil.PHONE_EXC;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapperImpl userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void resetMockRules() {
        Mockito.reset(userRepository);
        Mockito.reset(userMapper);
    }

    @Test
    void testFindAll() {
        // arrange
        var user1 = new User(
                1L,
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                Timestamp.from(Instant.now()),
                "JASO1921AS",
                "JI1O90KA1"
        );

        var user1Dto = new UserDto(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );

        // act
        when(userRepository.findAll())
                .thenReturn(List.of(user1));
        when(userMapper.entityToDtoList(List.of(user1)))
                .thenReturn(List.of(user1Dto));
        var user = userService.getAllUsers();

        // assert
        assertEquals(1, user.size());
        assertEquals("JASO1921AS", user.getFirst().getPassportId());
    }

    @Test
    void testUniqueEmail() {
        // arrange
        var user = new UserRequest(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );
        var userDto = new UserDto(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );

        // act
        when(userRepository.existsByEmail(user.email()))
                .thenReturn(true);
        when(userMapper.requestToDto(user))
                .thenReturn(userDto);

        // assert
        assertThrows(UniqueRecordException.class, () -> userService.regUser(user), EMAIL_EXC);
    }

    @Test
    void testUniquePhone() {
        // arrange
        var user = new UserRequest(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );
        var userDto = new UserDto(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );

        // act
        when(userRepository.existsByPhoneNumber(user.phoneNumber()))
                .thenReturn(true);
        when(userMapper.requestToDto(user))
                .thenReturn(userDto);

        // assert
        assertThrows(UniqueRecordException.class, () -> userService.regUser(user), PHONE_EXC);
    }

    @Test
    void testUniquePassport() {
        // arrange
        var user = new UserRequest(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );
        var userDto = new UserDto(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );

        // act
        when(userRepository.existsByPassportId(user.passportId()))
                .thenReturn(true);
        when(userMapper.requestToDto(user))
                .thenReturn(userDto);

        // assert
        assertThrows(UniqueRecordException.class, () -> userService.regUser(user), PASSPORT_EXC);
    }

    @Test
    void testUniqueDrivingLic() {
        // arrange
        var user = new UserRequest(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );
        var userDto = new UserDto(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );

        // act
        when(userRepository.existsByDrivingLicenseId(user.drivingLicenseId()))
                .thenReturn(true);
        when(userMapper.requestToDto(user))
                .thenReturn(userDto);

        // assert
        assertThrows(UniqueRecordException.class, () -> userService.regUser(user), DR_LICENSE_EXC);
    }

    @Test
    void testDeleteUserById() {
        // act
        when(userRepository.existsById(1L))
                .thenReturn(true);

        // assert
        assertDoesNotThrow(() -> userService.deleteUser(1L));
    }

    @Test
    void testDeleteUserByIdExc() {
        // act
        when(userRepository.existsById(1L))
                .thenReturn(false);

        // assert
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
    }

    @Test
    void testGetUserById() {
        // arrange
        var user = new UserDto(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );
        var userEntity = new User(
                1L,
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                Timestamp.from(Instant.now()),
                "JASO1921AS",
                "JI1O90KA1"
        );

        // act
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(userEntity));
        when(userMapper.entityToDto(userEntity))
                .thenReturn(user);

        // assert
        assertEquals(userService.getUserById(1L), user);
    }

    @Test
    void testGetUserByIdExc() {
        // act
        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        // assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testUpdateUser() {
        // arrange
        var user = new UserRequest(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );
        var userDto = new UserDto(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );
        var userEntity = new User(
                1L,
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                Timestamp.from(Instant.now()),
                "JASO1921AS",
                "JI1O90KA1"
        );

        // act
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity))
                .thenReturn(userEntity);
        when(userMapper.entityToDto(userEntity))
                .thenReturn(userDto);
        when(userMapper.requestToDto(user))
                .thenReturn(userDto);

        // assert
        assertEquals(userService.updateUser(1L, user), userDto);
    }

    @Test
    void testUpdateUserExc() {
        // arrange
        var user = new UserRequest(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );

        // act
        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        // assert
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, user));
    }
}
