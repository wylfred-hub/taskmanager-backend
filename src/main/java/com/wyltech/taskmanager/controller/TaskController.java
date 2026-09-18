package com.wyltech.taskmanager.controller;

import com.wyltech.taskmanager.dto.TaskRequest;
import com.wyltech.taskmanager.dto.TaskResponse;
import com.wyltech.taskmanager.service.TaskServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskServiceImpl taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Long> create(
            @RequestBody TaskRequest request
    ) {
        return new ResponseEntity<>(
                taskService.createTack(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String title
    ) {
        return ResponseEntity.ok(
                taskService.getAll(status, title)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                taskService.getOne(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(
            @PathVariable Long id,
            @RequestBody TaskRequest request
    ) {
        return ResponseEntity.ok(
                taskService.updateTask(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delTask(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                taskService.deleteTask(id)
        );
    }
}