package com.demo.cars.database;

import com.demo.cars.database.exception.UniqueRecordException;
import com.demo.cars.database.repository.UserRepository;
import com.demo.cars.database.service.impl.UserServiceImpl;
import com.demo.cars.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DbTest {
    private static final Logger log = LoggerFactory.getLogger(DbTest.class);
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserServiceImpl userService;

    @Test
    void test() {
        /*
        UserDto user = new UserDto(
                "test2",
                "test2",
                "test2@gmail.com",
                "+375295874765",
                "J61243",
                "184aka");

        try {
            userService.regUser(user);
        } catch (UniqueRecordException e) {
            throw new RuntimeException(e);
        }
         */
    }

    @Test
    void testSaved() {
        /*
        var user = repository.findAll();
        assertThat(user).isNotEmpty();

         */
    }

    @Test
    void testFindAll() {
        var users = userService.getAllUsers();
        assertThat(users).isNotEmpty();
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.getFirst().getFirstname()).isEqualTo("test2");
        assertThat(users.getFirst().getEmail()).isEqualTo("test2@gmail.com");
    }
}
