package com.example.dom.controller;

import com.example.dom.dto.user.RequestUser;
import com.example.dom.dto.user.ResponseUser;
import com.example.dom.exception.UserAlreadyExistException;
import com.example.dom.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ResponseUser>> getUsers() {
        List<ResponseUser> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable Long id) {
        return  ResponseEntity.ok(userService.getUser(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseUser> createUser(@Valid @RequestBody RequestUser data) throws UserAlreadyExistException {
        ResponseUser createdResponseUser = userService.createUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResponseUser);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
