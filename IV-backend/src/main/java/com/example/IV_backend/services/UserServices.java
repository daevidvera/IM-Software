package com.example.IV_backend.services;

import com.example.IV_backend.model.User_app;
import com.example.IV_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Service //this class is a service, make it available for dependency injection
public class UserServices implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User_app> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            var UserObj = user.get();
            return User.builder()
                    .username(UserObj.getUsername())
                    .password(UserObj.getPassword())
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found" + username);
        }

    }
}
