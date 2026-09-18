package com.wyltech.taskmanager.dto;

public record LoginRequest(
        String email,
        String password
) {
}
