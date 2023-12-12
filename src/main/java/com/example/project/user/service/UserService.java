package com.example.project.user.service;

import com.example.project.user.api.UserRequest;
import com.example.project.user.domain.User;
import com.example.project.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // Tutaj powinieneś użyć odpowiedniego mechanizmu skrótu hasła
        return userRepository.save(user);
    }
}