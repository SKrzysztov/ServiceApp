package com.example.project.user.controller;

import com.example.project.user.api.LoginRequest;
import com.example.project.user.api.RegisterRequest;
import com.example.project.user.domain.User;
import com.example.project.user.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> authenticatedUser = authenticationService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (authenticatedUser.isPresent()) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/login-basic")
    public ResponseEntity<String> loginBasicAuth(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader) {
        Optional<User> authenticatedUser = authenticationService.authenticateBasicAuth(authHeader);

        if (authenticatedUser.isPresent()) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {

        if (authenticationService.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this username already exists");
        }

        User newUser = authenticationService.createUser(registerRequest);
        return ResponseEntity.ok("Registration successful");
    }
}
