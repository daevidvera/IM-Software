package com.example.IV_backend.security;


import com.example.IV_backend.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // enables Spring Security for the application
public class SecurityConfig {


    private final UserServices userServices;

    public SecurityConfig(UserServices userServices) {
        this.userServices = userServices;
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return userServices;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); // this is what we use for auth
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Disable CSRF (Cross-Site Request Forgery) protection
                .csrf(csrf -> csrf.disable())

                // Configure which requests are allowed without authentication
                .authorizeHttpRequests(auth -> auth
                        // Allow anyone (even unauthenticated users) to POST to /api/auth/login
                        .requestMatchers("/api/auth/login").permitAll()
                        // Allow
                        .requestMatchers("/req/signup").permitAll()

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

                .authenticationProvider(authenticationProvider())
                // finalizing
                .build();
    }
}
