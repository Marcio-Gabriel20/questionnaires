package com.hiro.questionnaires.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hiro.questionnaires.dto.UserDto;
import com.hiro.questionnaires.dto.UserResponse;
import com.hiro.questionnaires.service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Void> newUser(@RequestBody UserDto dto) {
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

    @PutMapping("/user/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable UUID id, @RequestBody UserDto dto) {
        if(id != null && dto != null) {
            HttpStatus updateUser = userService.updateUser(id, dto);

            if(updateUser == HttpStatus.OK) {
                return ResponseEntity.status(200).build();
            } else if(updateUser == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(404).build();
            } else {
                return ResponseEntity.status(500).build();
            }
        } else {
            return ResponseEntity.status(422).build();
        }
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<Void> patchUpdateUser(@PathVariable UUID id, @RequestBody UserDto dto) {
        if(id != null && dto != null) {
            HttpStatus updateUser = userService.patchUpdateUser(id, dto);

            if(updateUser == HttpStatus.OK) {
                return ResponseEntity.status(200).build();
            } else if(updateUser == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(404).build();
            } else {
                return ResponseEntity.status(500).build();
            }
        } else {
            return ResponseEntity.status(422).build();
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        if(id != null) {
            HttpStatus deleteUser = userService.deleteUser(id);

            if (deleteUser == HttpStatus.OK) {
                return ResponseEntity.status(200).build();
            } else if(deleteUser == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(404).build();
            } else {
                return ResponseEntity.status(500).build();
            }
        } else {
            return ResponseEntity.status(422).build();
        }
    }
}