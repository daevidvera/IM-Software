package com.example.IV_backend.controller;

import com.example.IV_backend.dto.LoginRequest;
import com.example.IV_backend.model.User_app;
import com.example.IV_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User_app> userOpt = userRepository.findByUsername(request.getUsername()); // we find the user username

        if (userOpt.isPresent()) {
            User_app user = userOpt.get(); // we get it

            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.ok(Map.of("message", "Login successful"));
            }
            // we check if the password is equal to the password the user input in
        }

        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }
}
