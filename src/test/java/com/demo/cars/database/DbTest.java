package com.demo.cars.database;

import com.demo.cars.database.repository.UserRepository;
import com.demo.cars.database.service.UserServiceJpa;
import com.demo.cars.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DbTest {
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserServiceJpa userService;

    @Test
    void test() {
        var users = repository.findAll();
        assertThat(users).isEmpty();
        UserDto user = new UserDto(1L,
                "test",
                "test",
                "test@gmail.com",
                "+375293774765",
                Timestamp.from(Instant.ofEpochSecond(78212232)),
                "JS1242",
                "123aka");

        userService.regUser(user);
    }
}
