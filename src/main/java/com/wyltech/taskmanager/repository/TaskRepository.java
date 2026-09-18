package com.wyltech.taskmanager.repository;

import com.wyltech.taskmanager.dto.TaskResponse;
import com.wyltech.taskmanager.dto.UserResponse;
import com.wyltech.taskmanager.entity.Task;
import com.wyltech.taskmanager.entity.TaskStatus;
import com.wyltech.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(String status);

    List<Task> findByTitleContainingIgnoreCase(String title);

    List<Task> findByUser(User user);

    List<Task> findByUserAndStatus(User user, TaskStatus status);

    List<Task> findByUserAndTitleContainingIgnoreCase(
            User user,
            String title
    );
}
