package com.wyltech.taskmanager.mapper;

import com.wyltech.taskmanager.dto.TaskRequest;
import com.wyltech.taskmanager.dto.TaskResponse;
import com.wyltech.taskmanager.dto.UserRequest;
import com.wyltech.taskmanager.dto.UserResponse;
import com.wyltech.taskmanager.entity.Task;
import com.wyltech.taskmanager.entity.User;

public class TaskMapper {
    public static Task toEntity(TaskRequest request){
        return Task.builder()
                .title(request.title())
                .description(request.description())
                .status(request.status())
                .build();
    }

    public static TaskResponse toResponse(Task task){
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
