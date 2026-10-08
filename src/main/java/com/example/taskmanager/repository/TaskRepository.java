package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskPriority;
import com.example.taskmanager.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findTaskByUser_Id(Long id);
    List<Task> findTaskByStatus(TaskStatus status);
    List<Task> findByPriority(TaskPriority priority);


    List<Task> findByUser_IdAndStatus(
            Long userId,
            TaskStatus status
    );

}