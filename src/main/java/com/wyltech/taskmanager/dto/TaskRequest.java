package com.wyltech.taskmanager.dto;

import com.wyltech.taskmanager.entity.TaskStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskRequest(
        String title,
        String description,
        TaskStatus status
) {
}
