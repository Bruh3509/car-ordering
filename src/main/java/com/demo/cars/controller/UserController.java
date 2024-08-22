package com.demo.cars.controller;

import com.demo.cars.dto.UserDto;
import com.demo.cars.mapper.UserMapper;
import com.demo.cars.model.user.UserRequest;
import com.demo.cars.model.user.UserResponse;
import com.demo.cars.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "user-controller", description = "managing user logic")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;
    UserMapper userMapper;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserResponse> getUserInfo(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(userMapper.dtoToResponse(userService.getUserById(id)), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userMapper.dtoToResponse(userService.getAllUsers()), HttpStatus.OK);
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<Void> regNewUser(
            @RequestBody
            @Parameter(name = "new user post request", required = true)
            UserRequest request
    ) {
        userService.regUser(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(value = "{id}/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDto> updateUserInfo(
            @PathVariable
            Long id,
            @RequestBody
            @Parameter(name = "update user info request", required = true)
            UserRequest userRequest
    ) {
        var updateDto = userService.updateUser(id, userRequest);
        return new ResponseEntity<>(updateDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    ) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
