package com.example.IV_backend.controller;

import com.example.IV_backend.dto.LoginRequest;
import com.example.IV_backend.model.User_app;
import com.example.IV_backend.repository.UserRepository;
import com.example.IV_backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
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

            if (!user.isVerified()) {
                return ResponseEntity.status(403).body(Map.of("error", "Email not verified"));
            }

            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String token = JwtTokenUtil.generateToken(user.getUsername());
                return ResponseEntity.ok(Map.of(
                        "token", token,
                        "userId", user.getId()
                ));
            }


            // we check if the password is equal to the password the user input in
        }

        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }
}
