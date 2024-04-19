package com.hiro.questionnaires.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hiro.questionnaires.dto.CreateUserDto;
import com.hiro.questionnaires.entity.Role;
import com.hiro.questionnaires.entity.User;
import com.hiro.questionnaires.enums.RoleType;
import com.hiro.questionnaires.repository.RoleRepository;
import com.hiro.questionnaires.repository.UserRepository;

import jakarta.transaction.Transactional;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping("/users")
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto dto) {
        Role basicRole = roleRepository.findByName(RoleType.BASIC.name());
        
        Optional<User> userFromDb = userRepository.findByLogin(dto.login());

        if(userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = new User(dto.login(), passwordEncoder.encode(dto.password()), Set.of(basicRole));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}