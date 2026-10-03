package com.example.taskmanager.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;


    public Task(String description, String title, TaskStatus status) {
    }

//    public Task(@NotBlank(message = "title is required") @Size(max = 100, message = "title must not execeed 100 characters") String title, @Size(max = 500, message = "Description must not exceed 500 characters") String description, @NotNull(message = "Status is required") TaskStatus status) {
//    }
}
