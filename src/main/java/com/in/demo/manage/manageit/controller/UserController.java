package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.error.UserNotFoundException;
import com.in.demo.manage.manageit.mapper.UserMapper;
import com.in.demo.manage.manageit.model.User;
import com.in.demo.manage.manageit.model.dto.UserDTO;
import com.in.demo.manage.manageit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> allUsers = service.getAllUsers();

        List<UserDTO> dtos = allUsers.stream()
                .map(UserMapper::mapToUserDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws UserNotFoundException {
        User foundUser = service.getUserById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserMapper.mapToUserDTO(foundUser));
    }

    @PostMapping()
    public ResponseEntity<UserDTO> registerNewUser(@RequestBody User user) throws UserNotFoundException {
        User createdUser = service.addNewUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserMapper.mapToUserDTO(createdUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping()
    public ResponseEntity<UserDTO> updateUserInfo(@RequestBody User user) throws UserNotFoundException {
        User updatedUser = service.updateUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserMapper.mapToUserDTO(updatedUser));
    }
}
