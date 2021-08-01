package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.error.InvalidDataException;
import com.in.demo.manage.manageit.error.UserExistsException;
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
@CrossOrigin("http://localhost:4200")
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

    @GetMapping("/by/{id}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable(value = "id") String username) throws UserNotFoundException {
        User foundUser = service.getUserByUsername(username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserMapper.mapToUserDTO(foundUser));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserDTO> login(@RequestBody User user) throws DataNotFoundException, InvalidDataException {
        // todo ------ po skonfigurwaniu security do konca wywalic metode validate i ustawic jak trzeba
        User validatedUser = service.validateUser(user.getUsername(), user.getPassword());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserMapper.mapToUserDTO(validatedUser));
    }

    @PostMapping()
    public ResponseEntity<UserDTO> registerNewUser(@RequestBody User user) throws UserNotFoundException, UserExistsException {
        User createdUser = service.addNewUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserMapper.mapToUserDTO(createdUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable(value = "id") String username) {
        service.deleteUser(username);
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
