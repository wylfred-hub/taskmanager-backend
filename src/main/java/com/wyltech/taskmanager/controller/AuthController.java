package com.wyltech.taskmanager.controller;

import com.wyltech.taskmanager.dto.LoginRequest;
import com.wyltech.taskmanager.dto.UserRequest;
import com.wyltech.taskmanager.dto.UserResponse;
import com.wyltech.taskmanager.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @RequestBody UserRequest request
    ) {
        UserResponse response = authService.register(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest request
    ) {
        String token = authService.login(
                request.email(),
                request.password()
        );

        return ResponseEntity.ok(token);
    }
}