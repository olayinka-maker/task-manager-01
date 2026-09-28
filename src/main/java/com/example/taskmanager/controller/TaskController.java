package com.example.taskmanager.controller;


import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
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
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    //done
    @GetMapping("/api/tasks/{id}")
    public Task getTaskById(@PathVariable Long id) {

        return taskService.getTaskById(id);
    }


    //done
    @PostMapping("/api/create-task")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    //done
    @PutMapping("/api/updatetasks/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @RequestBody Task task
    ) {

        return taskService.updateTask(task, id);
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
