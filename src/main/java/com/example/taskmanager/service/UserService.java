package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskCreateRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.dto.UserDto.UserCreateRequest;
import com.example.taskmanager.dto.UserDto.UserResponse;
import com.example.taskmanager.exception.EmailAlreadyExistsException;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    private UserResponse toResponse(User user) {

        if(userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists" + user.getEmail()) ;
        }

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );

    }


    public UserResponse createUser(UserCreateRequest request) {

        User user = new User(
                request.getName(),
                request.getEmail()
        );


        User savedUser = userRepository.save(user);

        return toResponse(savedUser);

    }


}