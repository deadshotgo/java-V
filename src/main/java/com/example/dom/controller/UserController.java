package com.example.dom.controller;

import com.example.dom.dto.user.UserDto;
import com.example.dom.models.User;
import com.example.dom.service.UserService;
import com.example.dom.swagger.GenerateApiDoc;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "Endpoints for user management")
public class UserController {
    @Autowired
    private UserService userService;

    @GenerateApiDoc(
            summary = "Get all users",
            description = "Retrieve a list of all registered users",
            responseDescription = "List of users retrieved successfully",
            responseClass = User.class
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GenerateApiDoc(
            summary = "Get user by ID",
            description = "Retrieve a user by its unique identifier",
            responseDescription = "User retrieved successfully by ID",
            responseClass = User.class
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return  ResponseEntity.ok(userService.getUser(id));
    }

    @GenerateApiDoc(
            summary = "Register a new user",
            description = "Register a new user with the provided data",
            responseDescription = "User registered successfully",
            responseClass = User.class
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto data) {
        User createdResponseUser = userService.createUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResponseUser);
    }

    @GenerateApiDoc(
            summary = "Delete user by ID",
            description = "Delete a user by its unique identifier",
            responseDescription = "User deleted successfully",
            responseClass = Long.class
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
