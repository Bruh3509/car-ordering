package com.demo.cars.database;

import com.demo.cars.entity.User;
import com.demo.cars.exception.UniqueRecordException;
import com.demo.cars.exception.UserNotFoundException;
import com.demo.cars.repository.UserRepository;
import com.demo.cars.service.impl.UserServiceImpl;
import com.demo.cars.dto.UserDto;
import com.demo.cars.mapper.UserMapperImpl;
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

import static com.demo.cars.utility.PropertyUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapperImpl userMapper = new UserMapperImpl();
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
        given(userRepository.findAll())
                .willReturn(List.of(user1));
        given(userMapper.entityToDtoList(List.of(user1)))
                .willReturn(List.of(user1Dto));
        var user = userService.getAllUsers();

        // assert
        assertEquals(user.size(), 1);
        assertEquals(user.getFirst().getPassportId(), "JASO1921AS");
    }

    @Test
    void testUniqueEmail() {
        // arrange
        var user = new UserDto(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );

        // act
        given(userRepository.existsByEmail(user.getEmail()))
                .willReturn(true);

        // assert
        assertThrows(UniqueRecordException.class,
                () -> userService.regUser(user),
                EMAIL_EXC);
    }

    @Test
    void testUniquePhone() {
        // arrange
        var user = new UserDto(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );

        // act
        given(userRepository.existsByPhoneNumber(user.getPhoneNumber()))
                .willReturn(true);

        // assert
        assertThrows(UniqueRecordException.class,
                () -> userService.regUser(user),
                PHONE_EXC);
    }

    @Test
    void testUniquePassport() {
        // arrange
        var user = new UserDto(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );

        // act
        given(userRepository.existsByPassportId(user.getPassportId()))
                .willReturn(true);

        // assert
        assertThrows(UniqueRecordException.class,
                () -> userService.regUser(user),
                PASSPORT_EXC);
    }

    @Test
    void testUniqueDrivingLic() {
        // arrange
        var user = new UserDto(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );

        // act
        given(userRepository.existsByDrivingLicenseId(user.getDrivingLicenseId()))
                .willReturn(true);

        // assert
        assertThrows(UniqueRecordException.class,
                () -> userService.regUser(user),
                DR_LICENSE_EXC);
    }

    @Test
    void testDeleteUserById() {
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
        given(userRepository.findById(1L))
                .willReturn(Optional.of(userEntity));

        // assert
        userService.deleteUser(1L);
    }

    @Test
    void testDeleteUserByIdExc() {
        // arrange
        var user = new UserDto("Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1");

        // act
        given(userRepository.findById(1L))
                .willReturn(Optional.empty());

        // assert
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
    }

    @Test
    void testGetUserById() {
        // arrange
        var user = new UserDto("Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1");
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
        given(userRepository.findById(1L))
                .willReturn(Optional.of(userEntity));
        given(userMapper.entityToDto(userEntity))
                .willReturn(user);

        // assert
        assertEquals(userService.getUserById(1L), user);
    }

    @Test
    void testGetUserByIdExc() {
        // assert
        var user = new UserDto("Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1");
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
        given(userRepository.findById(1L))
                .willReturn(Optional.empty());

        // assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testUpdateUser() {
        // arrange
        var user = new UserDto("Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1");
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
        given(userRepository.findById(1L))
                .willReturn(Optional.of(userEntity));
        given(userMapper.dtoToEntity(user))
                .willReturn(userEntity);
        given(userRepository.save(userEntity))
                .willReturn(userEntity);
        given(userMapper.entityToDto(userEntity))
                .willReturn(user);

        // assert
        assertEquals(userService.updateUser(1L, user), user);
    }

    @Test
    void testUpdateUserExc() {
        // arrange
        var user = new UserDto("Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1");
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
        given(userRepository.findById(1L))
                .willReturn(Optional.empty());

        // assert
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, user));
    }
}
