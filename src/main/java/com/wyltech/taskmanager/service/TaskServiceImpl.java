package com.wyltech.taskmanager.service;

import com.wyltech.taskmanager.dto.TaskRequest;
import com.wyltech.taskmanager.dto.TaskResponse;
import com.wyltech.taskmanager.entity.Task;
import com.wyltech.taskmanager.entity.TaskStatus;
import com.wyltech.taskmanager.entity.User;
import com.wyltech.taskmanager.mapper.TaskMapper;
import com.wyltech.taskmanager.repository.TaskRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.wyltech.taskmanager.repository.UserRepository;


import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Long createTack(TaskRequest request) {

        User currentUser = getCurrentUser();

        Task task = TaskMapper.toEntity(request);

        task.setUser(currentUser);

        Task savedTask = taskRepository.save(task);

        return TaskMapper.toResponse(savedTask).id();
    }

    @Override
    public List<TaskResponse> getAll(String status, String title) {

        User currentUser = getCurrentUser();

        List<Task> tasks;

        if (status != null && !status.isBlank()) {

            TaskStatus taskStatus = TaskStatus.valueOf(
                    status.toUpperCase()
            );

            tasks = taskRepository.findByUserAndStatus(
                    currentUser,
                    taskStatus
            );

        } else if (title != null && !title.isBlank()) {

            tasks = taskRepository.findByUserAndTitleContainingIgnoreCase(
                    currentUser,
                    title
            );

        } else {

            tasks = taskRepository.findByUser(currentUser);
        }

        return tasks.stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    @Override
    public Long updateTask(Long id, TaskRequest request) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task non trouvée"));

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());

        Task saved = taskRepository.save(task);

        return saved.getId();
    }

    @Override
    public TaskResponse getOne(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Task no trouve"));

        return TaskMapper.toResponse(task);
    }

    @Override
    public Boolean deleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task non trouvée"));

        taskRepository.delete(task);

        return true;
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Utilisateur connecté introuvable"
                        )
                );
    }
}
