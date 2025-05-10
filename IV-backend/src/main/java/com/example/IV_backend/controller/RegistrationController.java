package com.example.IV_backend.controller;

import com.example.IV_backend.model.User_app;
import com.example.IV_backend.repository.UserRepository;
import com.example.IV_backend.services.EmailServices;
import com.example.IV_backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailServices emailServices;

    @PostMapping(value = "/req/signup", consumes = "application/json")
    public ResponseEntity<?> createUser(@RequestBody User_app user) {
        // check if username exists
        var existingUserByUsername = userRepository.findByUsername(user.getUsername());
        if (existingUserByUsername.isPresent()) {
            if (existingUserByUsername.get().isVerified()) {
                return ResponseEntity.status(409).body(Map.of("error", "Username already taken and verified"));
            } else {
                return ResponseEntity.status(409).body(Map.of("error", "Username already taken but not verified. Please verify your email."));
            }
        }

        // check if email exists
        var existingUserByEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserByEmail.isPresent()) {
            if (existingUserByEmail.get().isVerified()) {
                return ResponseEntity.status(409).body(Map.of("error", "Email already registered and verified"));
            } else {
                return ResponseEntity.status(409).body(Map.of("error", "Email already registered but not verified. Please verify your email."));
            }
        }

        //new user create
        String verificationToken = JwtTokenUtil.generateToken(user.getEmail());
        user.setVerificationToken(verificationToken);
        user.setVerified(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        emailServices.sendVerificationMail(user.getEmail(), verificationToken);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "User created. Please verify your email."));
    }


}
