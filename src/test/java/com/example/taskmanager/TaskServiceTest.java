package com.example.taskmanager;
import com.example.taskmanager.dto.TaskCreateRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskPriority;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.service.TaskService;
import org.junit.jupiter.api.function.Executable;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertThrows;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {


    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void getTaskById_shouldThrowExceptionWhenTaskDoesNotExist() {

        // Arrange: make the fake repository return no task
        when(taskRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act + Assert: verify the expected exception
        Assert.assertThrows(
                TaskNotFoundException.class,
                () -> taskService.getTaskById(999L)
        );
    }


    @Test
    void getTaskById_shouldReturnTaskWhenTaskExists() {
        // Arrange: create a task that the mock repository will return
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Learn Spring Boot");
        task.setDescription("Understand service testing");
        task.setStatus(TaskStatus.IN_PROGRESS);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        // Act: call the service
        TaskResponse response = taskService.getTaskById(1L);

        // Assert: verify the returned data
        assertEquals(1L, response.getId());
        assertEquals("Learn Spring Boot", response.getTitle());
        assertEquals("Understand service testing", response.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, response.getStatus());

        verify(taskRepository).findById(1L);
    }



    @Test
    void createTask_shouldSaveTaskAndReturnResponse() {
        // Arrange: create an existing user
        User user = new User();
        user.setId(1L);
        user.setName("Olayinka");
        user.setEmail("olayinka@example.com");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        // Prepare the task request
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Learn Spring Boot Testing");
        request.setDescription("Write unit tests with Mockito");
        request.setStatus(TaskStatus.TODO);
        request.setPriority(TaskPriority.HIGH);
        request.setUserId(1L);

        // Arrange: simulate saving the task
        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> {
                    Task task = invocation.getArgument(0);
                    task.setId(10L);
                    return task;
                });

        // Act: call the service
        TaskResponse response = taskService.createTask(request);

        // Assert: check the response
        assertEquals(10L, response.getId());
        assertEquals("Learn Spring Boot Testing", response.getTitle());
        assertEquals(TaskStatus.TODO, response.getStatus());
        assertEquals(TaskPriority.HIGH, response.getPriority());
        assertEquals(1L, response.getUser().getId());
    }


    @Test
    void createTask_shouldThrowExceptionWhenUserDoesNotExist() {
        // Arrange
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Learn Spring Boot Testing");
        request.setDescription("Test missing user");
        request.setStatus(TaskStatus.TODO);
        request.setPriority(TaskPriority.HIGH);
        request.setUserId(999L);

        when(userRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                TaskNotFoundException.class,
                (Executable) () -> taskService.createTask(request)
        );

        verify(taskRepository, never()).save(any(Task.class));
    }


}
