package com.example.taskmanager.dto;


import com.example.taskmanager.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskUpdateRequest {

    @NotBlank(message = "title is required")
    @Size(max = 100, message = "title can not exceed 100 characters")
    private String title;

    @Size(max = 100, message = "Description can not exceed 100 characters")
    private String description;

    @NotNull(message = "Status is required")
    private TaskStatus status;
}
