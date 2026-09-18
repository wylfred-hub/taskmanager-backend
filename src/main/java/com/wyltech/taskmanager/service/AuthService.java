package com.wyltech.taskmanager.service;

import com.wyltech.taskmanager.dto.UserRequest;
import com.wyltech.taskmanager.dto.UserResponse;
import com.wyltech.taskmanager.entity.User;
import com.wyltech.taskmanager.repository.UserRepository;
import com.wyltech.taskmanager.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // =========================
    // REGISTER
    // =========================

    public UserResponse register(UserRequest request) {

        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Ce username existe déjà");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Cet email existe déjà");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .build();
    }

    // =========================
    // LOGIN
    // =========================

    public String login(String email, String password) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                email,
                                password
                        )
                );

        return jwtService.generateToken(authentication.getName());
    }
}