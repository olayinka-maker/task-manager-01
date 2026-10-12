package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskPriority;
import com.example.taskmanager.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(
            """
                SELECT t FROM Task t
                WHERE (:status IS NULL OR t.status = :status)
                AND(:priority IS NULL OR t.priority = :priority)
                AND (:title IS NULL OR
                                   LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')))
                              AND (:userId IS NULL OR t.user.id = :userId)

                """
    )
    Page<Task>  searchTasks(
            @Param("status") TaskStatus  status,
            @Param("priority") TaskPriority priority,
            @Param("title")  String  title,
            @Param("userId") Long userId,
            Pageable pageable
    );


    List<Task> findTaskByUser_Id(Long id);
    List<Task> findTaskByStatus(TaskStatus status);
    List<Task> findByPriority(TaskPriority priority);


    List<Task> findByUser_IdAndStatus(
            Long userId,
            TaskStatus status
    );

}