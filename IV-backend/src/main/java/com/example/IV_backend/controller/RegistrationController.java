package com.example.IV_backend.controller;

import com.example.IV_backend.model.User_app;
import com.example.IV_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/req/signup", consumes = "application/json")
    public ResponseEntity<?> createUser(@RequestBody User_app user) {

         // check if the username exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("error", "Username already exists"));
        }

        // we check if the email exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("error", "Email already in use"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); //we encode the password

        //save and we return the user
        User_app savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }
}
