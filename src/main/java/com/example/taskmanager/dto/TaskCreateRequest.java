package com.example.taskmanager.dto;


import com.example.taskmanager.model.TaskPriority;
import com.example.taskmanager.model.TaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TaskCreateRequest {


    @NotBlank(message = "title is required")
    @Size(max = 100, message = "title must not execeed 100 characters")
    private String title;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;


    @NotNull(message = "Priority is required")
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;


    private LocalDate dueDate;

    @NotNull(message = "User ID is required")
    private Long userId;
}
