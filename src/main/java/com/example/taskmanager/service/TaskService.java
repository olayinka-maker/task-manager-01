package com.example.taskmanager.service;


import com.example.taskmanager.dto.TaskCreateRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.dto.TaskUpdateRequest;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {


    private final TaskRepository taskRepository;


    private TaskResponse toResponse(Task task) {

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );
    };



    public TaskResponse createTask(TaskCreateRequest request) {

        Task updatesTask = new Task(
                request.getDescription(),
                request.getTitle(),
                request.getStatus()
        );

        Task savedTask = taskRepository.save(updatesTask);
        return toResponse(savedTask);

    }

    public List<TaskResponse> getTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(()->
                    new TaskNotFoundException("Task with id "+id+" not found")
                );
        return toResponse(task);

    }

    public TaskResponse updateTask(TaskUpdateRequest updateTask, Long id) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(()->
                new TaskNotFoundException("Task with id "+id+" not found")
        );

        existingTask.setDescription(updateTask.getDescription());
        existingTask.setTitle(updateTask.getTitle());
        existingTask.setStatus(updateTask.getStatus());

        Task updatedTask =  taskRepository.save(existingTask);

        return toResponse(updatedTask);


    }

    public boolean deleteTask(Long id) {

        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(
                    "Task not found with id: " + id
            );        }

        taskRepository.deleteById(id);
        return true;
    }

 }
