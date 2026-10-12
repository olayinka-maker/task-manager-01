package com.example.taskmanager.service;


import com.example.taskmanager.dto.TaskCreateRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.dto.TaskUpdateRequest;
import com.example.taskmanager.dto.UserDto.UserResponse;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskPriority;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskService {


    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private TaskResponse toResponse(Task task) {

        UserResponse userResponse = null;

        if (task.getUser() != null) {
            userResponse = new UserResponse(
                    task.getUser().getId(),
                    task.getUser().getName(),
                    task.getUser().getEmail()
            );
        }

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getDueDate(),
                userResponse
        );
    }


    public  Page<TaskResponse>  searchTasks(TaskStatus status,TaskPriority priority,String title, Long userId,Pageable pageable){
        return taskRepository.searchTasks(status,priority,title,userId,pageable)
                .map(this::toResponse);
    }


    public TaskResponse createTask(TaskCreateRequest request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(
                ()->
                    new TaskNotFoundException("User not found" + request.getUserId())

        );

          Task task = new Task(
                request.getTitle(),
                request.getDescription(),
                request.getStatus(),
                  request.getPriority(),
                  request.getDueDate()
        );

          task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return toResponse(savedTask);

    }

    public Page<TaskResponse> getTasks(Pageable pageable) {
       return taskRepository.findAll(pageable)
               .map(this::toResponse);

    }

    public TaskResponse getTaskById(Long id) {
        Task mytask = taskRepository.findById(id)
                .orElseThrow(()->
                    new TaskNotFoundException("Task with id "+id+" not found")
                );

        return toResponse(mytask);
    }

    public TaskResponse updateTask(TaskUpdateRequest updateTask, Long id) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(()->
                new TaskNotFoundException("Task with id "+id+" not found")
        );

        if(existingTask != null){
             existingTask.setDescription(updateTask.getDescription());
             existingTask.setTitle(updateTask.getTitle());
             existingTask.setStatus(updateTask.getStatus());

             Task updatedTask = taskRepository.save(existingTask);

             return  toResponse(updatedTask);

         }


         return null;
    }

    public List<TaskResponse> geTasksByUserId(Long user_id){
        return  taskRepository.findTaskByUser_Id(user_id).
                stream()
                .map(this::toResponse)
                .toList();
    }

    public List<TaskResponse> geTasksByStatus(TaskStatus status){
        return  taskRepository.findTaskByStatus(status).
                stream()
                .map(this::toResponse)
                .toList();
    }

    public List<TaskResponse> getTasksByPriority(
            TaskPriority priority
    ) {

        return taskRepository.findByPriority(priority)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<TaskResponse> getTasksByIDandStatus(
            Long userid, TaskStatus status
    ) {
            return taskRepository.findByUser_IdAndStatus(userid,status)
                    .stream()
                    .map(this::toResponse)
                    .toList();
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
