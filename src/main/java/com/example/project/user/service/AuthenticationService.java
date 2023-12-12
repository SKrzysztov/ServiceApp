package com.example.project.user.service;

import com.example.project.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    public Optional<User> authenticate(String username, String password) {
        Optional<User> user = userService.findByUsername(username);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }

        return Optional.empty();
    }

    public Optional<User> authenticateBasicAuth(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String base64Credentials = authHeader.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
            String[] parts = credentials.split(":", 2);
            String username = parts[0];
            String password = parts[1];

            return authenticate(username, password);
        }

        return Optional.empty();
    }

    public User createUser(String username, String password) {
        return userService.createUser(username, password);
    }
}
