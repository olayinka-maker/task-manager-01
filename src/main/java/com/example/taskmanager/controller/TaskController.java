package com.example.taskmanager.controller;


import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.dto.TaskCreateRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.dto.TaskUpdateRequest;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    //done
    @GetMapping("/api/tasks")
    public List<TaskResponse> getTasks() {
        return taskService.getTasks();
    }

    //done
    @GetMapping("/api/tasks/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {

        return taskService.getTaskById(id);
    }


    //done
    @PostMapping("/api/create-task")
    public TaskResponse createTask(@RequestBody @Valid TaskCreateRequest request) {

        return taskService.createTask(request);
    }

    //done
    @PutMapping("/api/updatetasks/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @RequestBody @Valid TaskUpdateRequest request
    ) {


        return taskService.updateTask(request, id);
    }

    //done
    @DeleteMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable Long id) {

        boolean deleted = taskService.deleteTask(id);

        if (deleted) {
            return "Task " + id + " deleted";
        }

        return "Task " + id + " not found";
    }
    }
