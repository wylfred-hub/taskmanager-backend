package com.wyltech.taskmanager.mapper;

import com.wyltech.taskmanager.dto.UserRequest;
import com.wyltech.taskmanager.dto.UserResponse;
import com.wyltech.taskmanager.entity.User;

public class UserMapper {
    public static User toEntity(UserRequest request){
        return User.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .build();
    }

    public static UserResponse toResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
