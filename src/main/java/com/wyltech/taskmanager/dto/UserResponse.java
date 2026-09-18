package com.wyltech.taskmanager.dto;

import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String username,
        String email
) {
}
