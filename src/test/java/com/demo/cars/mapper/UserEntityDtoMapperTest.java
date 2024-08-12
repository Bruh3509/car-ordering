package com.demo.cars.mapper;

import com.demo.cars.entity.User;
import com.demo.cars.dto.UserDto;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserEntityDtoMapperTest {
    private final UserMapper mapper = new UserMapperImpl();

    @Test
    void testEntityDto() {
        User user = new User(1L,
                "ivan",
                "utr",
                "utr@",
                "123",
                Timestamp.from(Instant.now()),
                "qwerty",
                "trewq");
        UserDto userDto = mapper.entityToDto(user);
        comparator(user, userDto);
    }

    @Test
    void testDtoEntity() {
        UserDto userDto = new UserDto("ivan",
                "utr",
                "utr@",
                "123",
                "qwerty",
                "trewq");
        User user = mapper.dtoToEntity(userDto);
        comparator(user, userDto);
    }

    private void comparator(User user, UserDto userDto) {
        assertEquals(userDto.getFirstname(), user.getFirstname());
        assertEquals(userDto.getLastname(), user.getLastname());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(userDto.getPassportId(), user.getPassportId());
        assertEquals(userDto.getDrivingLicenseId(), user.getDrivingLicenseId());
    }
}
