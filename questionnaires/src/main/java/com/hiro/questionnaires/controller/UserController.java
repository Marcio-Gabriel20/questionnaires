package com.hiro.questionnaires.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hiro.questionnaires.dto.CreateUserDto;
import com.hiro.questionnaires.dto.UserResponse;
import com.hiro.questionnaires.service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto dto) {
        if(dto != null && !dto.login().isBlank() && !dto.password().isBlank()) {
            HttpStatus newUser = userService.newUser(dto);

            if(newUser == HttpStatus.CREATED) {
                return ResponseEntity.status(201).build();
            } else if(newUser == HttpStatus.CONFLICT) {
                return ResponseEntity.status(409).build();
            } else {
                return ResponseEntity.status(500).build();
            }
        } else {
            return ResponseEntity.status(422).build();
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> listUsers() {
        List<UserResponse> users = userService.listUsers();

        if(users == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(users);
    }
}