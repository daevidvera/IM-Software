package com.example.IV_backend.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // enables Spring Security for the application
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Disable CSRF (Cross-Site Request Forgery) protection
                .csrf(csrf -> csrf.disable())

                // Configure which requests are allowed without authentication
                .authorizeHttpRequests(auth -> auth
                        // Allow anyone (even unauthenticated users) to POST to /api/auth/login
                        .requestMatchers("/api/auth/login").permitAll()

                        // All other requests require the user to be authenticated
                        .anyRequest().authenticated()
                )

                // Configure session management to be stateless
                // This means Spring Security won’t create or use HTTP sessions
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                org.springframework.security.config.http.SessionCreationPolicy.STATELESS
                        )
                )

                // finalizing
                .build();
    }
}
