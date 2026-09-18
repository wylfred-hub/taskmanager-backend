package com.wyltech.taskmanager.dto;

import com.wyltech.taskmanager.entity.TaskStatus;
import com.wyltech.taskmanager.entity.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
