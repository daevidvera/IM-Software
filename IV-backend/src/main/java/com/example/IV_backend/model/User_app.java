package com.example.IV_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// model of our User, what value it has on our database etc
@Setter
@Getter
@Entity
@Table(name = "app_users")
public class User_app {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String verificationToken;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;


    @Column(name = "reset_token")
    private String resetToken;


}
