package com.demo.cars.database;

import com.demo.cars.database.exception.UniqueRecordException;
import com.demo.cars.database.exception.UserNotFoundException;
import com.demo.cars.database.service.UserService;
import com.demo.cars.dto.UserDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Rollback // почему @Rollback не работает без @Transactional? (@Transactional есть в сервисе)
public class DbTest extends IntegrationTest {
    @Autowired
    private UserService userService;

    @Test
    void testFindAllAndReg() throws UniqueRecordException {
        // empty table
        assertThat(userService.getAllUsers()).isEmpty();

        // add record
        var user1 = new UserDto(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );
        userService.regUser(user1);
        var user = userService.getAllUsers();
        assertEquals(user.size(), 1);

        // add one more record
        var user2 = new UserDto(
                "Vanya",
                "Ivanov",
                "ivanov@innowise.com",
                "+375294758192",
                "O1ID10DS",
                "OPAI491021A"
        );
        userService.regUser(user2);
        var users2 = userService.getAllUsers();
        assertEquals(users2.size(), 2);
    }

    @Test
    void testRegUserUniqueExc() throws UniqueRecordException {
        // arrange
        var user = new UserDto(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );
        var userEmailExc = new UserDto(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com", // the same email
                "+375293555612",
                "CASO1921AS",
                "KI1O90KA1"
        );
        var userPhoneExc = new UserDto(
                "Petya",
                "Sidorov",
                "orov@innowise.com",
                "+375293455612", // the same phone
                "CASO1921AS",
                "KI1O90KA1"
        );
        var userPassportExc = new UserDto(
                "Petya",
                "Sidorov",
                "orov@innowise.com",
                "+375293555612",
                "JASO1921AS", // the same passport
                "kI1O90KA1"
        );
        var userDrivingLicExc = new UserDto(
                "Petya",
                "Sidorov",
                "orov@innowise.com",
                "+375293555612",
                "CASO1921AS",
                "JI1O90KA1" // the same driving license
        );

        // act
        userService.regUser(user);

        // assert

        // email
        assertThrows(UniqueRecordException.class,
                () -> userService.regUser(userEmailExc),
                UniqueRecordException.EMAIL_EXC);

        // phone
        assertThrows(UniqueRecordException.class,
                () -> userService.regUser(userPhoneExc),
                UniqueRecordException.PHONE_EXC);

        // passport
        assertThrows(UniqueRecordException.class,
                () -> userService.regUser(userPassportExc),
                UniqueRecordException.PASSPORT_EXC);

        // driving license
        assertThrows(UniqueRecordException.class,
                () -> userService.regUser(userDrivingLicExc),
                UniqueRecordException.DR_LICENSE_EXC);
    }

    @Test
    void testDeleteUserById() throws UniqueRecordException, UserNotFoundException {
        // arrange
        var user = new UserDto(
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                "JASO1921AS",
                "JI1O90KA1"
        );

        userService.regUser(user);
        assertEquals(userService.getAllUsers().size(), 1);

        userService.deleteUser(1L); // немного не понятно откуда должно браться id,
        // если мы его инкапсулировали в User entity, как его монжо из вне доставать?
        assertThat(userService.getAllUsers()).isEmpty();
    }
}
