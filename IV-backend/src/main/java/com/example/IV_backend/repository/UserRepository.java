package com.example.IV_backend.repository;

import com.example.IV_backend.model.User_app;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User_app, Long> {

    Optional<User_app> findByUsername(String username); // allows us to retrieve a specific user by their ID
    Optional<User_app> findByEmail(String email);



}
