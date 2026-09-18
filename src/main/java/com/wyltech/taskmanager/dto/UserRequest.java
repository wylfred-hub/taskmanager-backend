package com.wyltech.taskmanager.dto;

import lombok.Builder;

@Builder
public record UserRequest(
        String username,
        String email,
        String password
) {
}
