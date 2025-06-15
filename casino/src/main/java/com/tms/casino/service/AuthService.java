package com.tms.casino.service;

import com.tms.casino.model.User;
import com.tms.casino.model.dto.AuthRequest;
import com.tms.casino.model.dto.AuthResponse;
import com.tms.casino.model.dto.RegisterRequest;
import com.tms.casino.repository.UserRepository;
import com.tms.casino.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        // Check if a user with the given username or email already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Create a new user entity and encode the password
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.valueOf("USER"));

        // Save the new user in the database
        userRepository.save(user);

        // Generate a JWT token for the new user
        String jwtToken = jwtService.generateToken(user);

        // Return the JWT token wrapped in AuthResponse
        return new AuthResponse(jwtToken);
    }

    public AuthResponse authenticate(AuthRequest request) {

        // Authenticate the user credentials (username and password)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Retrieve the user from the database by username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate a JWT token for the authenticated user
        String jwtToken = jwtService.generateToken(user);

        // Return the JWT token wrapped in AuthResponse
        return new AuthResponse(jwtToken);

    }
}
