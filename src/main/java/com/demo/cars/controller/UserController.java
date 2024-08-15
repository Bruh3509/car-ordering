package com.demo.cars.controller;

import com.demo.cars.mapper.UserMapper;
import com.demo.cars.model.user.UserRequest;
import com.demo.cars.model.user.UserResponse;
import com.demo.cars.service.UserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserResponse> getUserInfo(@PathVariable Long id) {
        return new ResponseEntity<>(userMapper.dtoToResponse(userService.getUserById(id)), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userMapper.dtoToResponse(userService.getAllUsers()), HttpStatus.OK);
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<Void> regNewUser(@RequestBody UserRequest request) {
        userService.regUser(userMapper.requestToDto(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "{id}/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserResponse> updateUserInfo(
            @PathVariable Long id,
            @RequestBody UserRequest userRequest
    ) {
        var updateDto = userService.updateUser(id, userMapper.requestToDto(userRequest));
        return new ResponseEntity<>(userMapper.dtoToResponse(updateDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
