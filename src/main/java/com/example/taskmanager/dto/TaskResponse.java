package com.example.taskmanager.dto;


import com.example.taskmanager.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

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

}
