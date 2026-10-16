
package com.example.taskmanager.controller;

import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.service.TaskService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Test
    void getTaskById_shouldReturn404WhenTaskDoesNotExist()
            throws Exception {

        // Arrange
        when(taskService.getTaskById(999L))
                .thenThrow(
                        new TaskNotFoundException("Task not found: 999")
                );

        // Act + Assert
        mockMvc.perform(get("/api/tasks/999"))
                .andExpect(status().isNotFound());
    }
}
