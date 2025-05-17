package com.example.IV_backend.controller;

import com.example.IV_backend.dto.ResetPasswordRequest;
import com.example.IV_backend.model.User_app;
import com.example.IV_backend.repository.UserRepository;
import com.example.IV_backend.services.EmailServices;
import com.example.IV_backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/req")
public class PasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Send password reset email
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        Optional<User_app> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "No account found with that email."));
        }

        User_app user = userOpt.get();
        String resetToken = JwtTokenUtil.generateToken(email);
        user.setResetToken(resetToken);
        userRepository.save(user);

        emailServices.sendForgotPasswordMail(email, resetToken);
        return ResponseEntity.ok(Map.of("message", "Reset link sent to your email."));
    }

    /**
     * Confirm new password via token
     */
    @PostMapping("/reset-password/confirm")
    public ResponseEntity<?> confirmReset(@RequestBody ResetPasswordRequest request) {
        String token = request.getResetToken();
        String newPassword = request.getPassword();

        String email;
        try {
            email = JwtTokenUtil.extractUsername(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid or expired token."));
        }

        Optional<User_app> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "User not found."));
        }

        User_app user = userOpt.get();

        if (user.getResetToken() == null || !user.getResetToken().equals(token)) {
            return ResponseEntity.status(403).body(Map.of("error", "Invalid reset token."));
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Password reset successfully."));
    }
}
