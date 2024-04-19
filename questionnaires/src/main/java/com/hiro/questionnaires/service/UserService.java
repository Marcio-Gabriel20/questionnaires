package com.hiro.questionnaires.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hiro.questionnaires.dto.CreateUserDto;
import com.hiro.questionnaires.entity.Role;
import com.hiro.questionnaires.entity.User;
import com.hiro.questionnaires.enums.RoleType;
import com.hiro.questionnaires.repository.RoleRepository;
import com.hiro.questionnaires.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public HttpStatus newUser(CreateUserDto dto) {
        try {
            Role basicRole = roleRepository.findByName(RoleType.BASIC.name());

            Optional<User> userFromDb = userRepository.findByLogin(dto.login());

            if(userFromDb.isPresent()) {
                return HttpStatus.CONFLICT;
            }

            User user = new User(dto.login(), passwordEncoder.encode(dto.password()), Set.of(basicRole));

            userRepository.save(user);

            return HttpStatus.CREATED;
        } catch (Exception e) {
            e.printStackTrace();

            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}