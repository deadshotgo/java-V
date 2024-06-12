package com.example.dom.controller;

import com.example.dom.dto.auth.RequestAuth;
import com.example.dom.dto.auth.ResponseAuth;
import com.example.dom.dto.user.UserDto;
import com.example.dom.models.User;
import com.example.dom.exception.UserAlreadyExistException;
import com.example.dom.service.JwtService;
import com.example.dom.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/generate-token")
    public ResponseEntity<ResponseAuth> authenticateAndGetToken(@Valid @RequestBody RequestAuth requestAuth) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestAuth.getUsername(), requestAuth.getPassword()));
        if (authentication.isAuthenticated()) {
            ResponseAuth response = new ResponseAuth(jwtService.generateToken(requestAuth.getUsername()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }


    @PostMapping("/create-user")
    public ResponseEntity<ResponseAuth> registrationUser(@Valid @RequestBody UserDto requestUser) throws UserAlreadyExistException {
        User responseUser = userService.createUserHasAuth(requestUser);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestUser.getUsername(), requestUser.getPassword()));
        if (authentication.isAuthenticated()){
            ResponseAuth response = new ResponseAuth(jwtService.generateToken(responseUser.getUsername()), responseUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }
}
