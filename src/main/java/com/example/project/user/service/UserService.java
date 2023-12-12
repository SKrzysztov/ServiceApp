package com.example.project.user.service;

import com.example.project.user.api.UserRequest;
import com.example.project.user.domain.User;
import com.example.project.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public User createUser(UserRequest userRequest) {
        User userEntity = new User();
        userEntity.setUsername(userRequest.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userRepository.save(userEntity);
    }

    public User updateUser(Long userId, UserRequest userRequest) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User userEntity = optionalUser.get();
            userEntity.setUsername(userRequest.getUsername());
            userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            // aktualizacja innych pól...

            return userRepository.save(userEntity);
        } else {
            // Obsługa błędu - użytkownik nie istnieje
            throw new RuntimeException("Użytkownik o podanym ID nie istnieje");
        }
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public boolean authenticateUser(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] parts = credentials.split(":", 2);
            String username = parts[0];
            String password = parts[1];
            Optional<User> user = userRepository.findByUsername(username);

            return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
        }
        return false;
    }
}