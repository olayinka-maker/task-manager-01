package com.example.taskmanager.dto;


import com.example.taskmanager.dto.UserDto.UserResponse;
import com.example.taskmanager.model.TaskPriority;
import com.example.taskmanager.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private UserResponse user;
    private LocalDate dueDate;
    private TaskPriority priority;
    private LocalDateTime createdAt;
    private LocalDate updatedAt;


    public TaskResponse(Long id, String title, String description, TaskStatus status, TaskPriority priority, LocalDateTime createdAt, LocalDate updatedAt, LocalDate dueDate, UserResponse userResponse) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.updatedAt = updatedAt;
        this.user = userResponse;

    }
}
