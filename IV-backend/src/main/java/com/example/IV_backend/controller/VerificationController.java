package com.example.IV_backend.controller;

import com.example.IV_backend.model.User_app;
import com.example.IV_backend.repository.UserRepository;
import com.example.IV_backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
public class VerificationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/req/signup/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        try {
            String emailString = JwtTokenUtil.extractUsername(token);
            Optional<User_app> userOpt = userRepository.findByEmail(emailString);

            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            User_app user = userOpt.get();

            // Check if already verified first
            if (user.isVerified()) {
                return ResponseEntity.ok("Already verified");
            }

            if (user.getVerificationToken() == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token expired");
            }

            if (!jwtTokenUtil.validateToken(token) || !token.equals(user.getVerificationToken())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");
            }

         // validate token
            user.setVerificationToken(null);
            user.setVerified(true);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("User verified");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification failed");
        }
    }
}
