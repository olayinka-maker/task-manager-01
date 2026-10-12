package com.example.taskmanager.controller;


import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.dto.TaskCreateRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.dto.TaskUpdateRequest;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskPriority;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;


    @PostMapping("/create-task")
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskCreateRequest request) {

        TaskResponse createdTask = taskService.createTask(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping("/getTasks")
    public Page<TaskResponse> getTasks(@RequestParam(required = false) TaskStatus status,
                                       @RequestParam(required = false) TaskPriority priority,
                                       @RequestParam(required = false) String title,
                                       @RequestParam(required = false) Long userId,
                                       @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    )  {
        return taskService.searchTasks(status,priority,title,userId,pageable);
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {

        return taskService.getTaskById(id);
    }

    @PutMapping("/updatetasks/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @RequestBody @Valid TaskUpdateRequest request
    ) {


        TaskResponse updatedTask =  taskService.updateTask(request, id);

        return ResponseEntity.ok(updatedTask);
    }

    @GetMapping("/user/{userId}")
    public  List<TaskResponse>  getTasksByUserId(@PathVariable Long userId){
          return taskService.geTasksByUserId(userId);
    }

    @GetMapping("/status/{status}")
    public  List<TaskResponse>  getTasksByStatus(@PathVariable TaskStatus status){
        return taskService.geTasksByStatus(status);
    }

    @GetMapping("/priority/{priority}")
    public List<TaskResponse> getTasksByPriority(
            @PathVariable TaskPriority priority
    ) {
        return taskService.getTasksByPriority(priority);
    }

    @GetMapping("/user/{userId}/status/{status}")
    public List<TaskResponse> getUserTasksByStatus(
            @PathVariable Long userId,
            @PathVariable TaskStatus status
    ) {
        return taskService.getTasksByIDandStatus(userId, status);
    }

    //done
    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    }
