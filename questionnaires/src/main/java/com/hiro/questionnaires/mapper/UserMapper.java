package com.hiro.questionnaires.mapper;

import com.hiro.questionnaires.dto.UserResponse;
import com.hiro.questionnaires.entity.User;

public class UserMapper {
    private static UserMapper instance;

    private UserMapper() {
    }

    public static UserMapper getInstance() {
        if(UserMapper.instance == null) {
            UserMapper.instance = new UserMapper();
        }

        return UserMapper.instance;
    }

    public UserResponse entityToResponse(User entity) {
        if(entity == null) {
            return null;
        }

        return new UserResponse(entity.getUserId(), entity.getLogin(), entity.getRoles());
    }
}