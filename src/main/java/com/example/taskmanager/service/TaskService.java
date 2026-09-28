package com.example.taskmanager.service;


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



    public Task createTask(Task task) {
        return taskRepository.save(task);

    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task updateTask(Task updateTask,Long id) {
        Task existingTask = taskRepository.findById(id).orElse(null);
         if(existingTask != null){
             existingTask.setDescription(updateTask.getDescription());
             existingTask.setTitle(updateTask.getTitle());
             existingTask.setStatus(updateTask.getStatus());

             return taskRepository.save(existingTask);

         }


         return null;
    }

    public boolean deleteTask(Long id) {

        if (!taskRepository.existsById(id)) {
            return false;
        }

        taskRepository.deleteById(id);
        return true;
    }

 }
