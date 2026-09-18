package com.wyltech.taskmanager.service;

import com.wyltech.taskmanager.dto.TaskRequest;
import com.wyltech.taskmanager.dto.TaskResponse;
import com.wyltech.taskmanager.entity.Task;

import java.util.List;

public interface TaskService {
    Long createTack(TaskRequest request);
    List<TaskResponse> getAll(String status, String title);
    Long updateTask(Long id, TaskRequest request);
    TaskResponse getOne(Long id);
    Boolean deleteTask(Long id);
}
