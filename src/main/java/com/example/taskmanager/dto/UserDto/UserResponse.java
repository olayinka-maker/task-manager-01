package com.example.taskmanager.dto.UserDto;


import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    private  Long id;
    private  String name;
    private  String email;
}
