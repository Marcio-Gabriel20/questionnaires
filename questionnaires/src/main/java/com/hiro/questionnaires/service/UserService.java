package com.hiro.questionnaires.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hiro.questionnaires.dto.UserDto;
import com.hiro.questionnaires.dto.UserResponse;
import com.hiro.questionnaires.entity.Role;
import com.hiro.questionnaires.entity.User;
import com.hiro.questionnaires.enums.RoleType;
import com.hiro.questionnaires.mapper.UserMapper;
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
    public HttpStatus newUser(UserDto dto) {
        try {
            Role userRole = roleRepository.findByName(RoleType.USER.name());

            Optional<User> userFromDb = userRepository.findByLogin(dto.login());

            if(userFromDb.isPresent()) {
                return HttpStatus.CONFLICT;
            }

            User user = new User();
            user.setLogin(dto.login());
            user.setPassword(passwordEncoder.encode(dto.password()));
            user.setRoles(Set.of(userRole));

            userRepository.save(user);

            return HttpStatus.CREATED;
        } catch (Exception e) {
            e.printStackTrace();

            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public List<UserResponse> listUsers() {
        try {
            List<User> usersFromDb = userRepository.findAll();
            List<UserResponse> users = new ArrayList<>();

            if(usersFromDb.isEmpty()) {
                return null;
            }

            for (User user : usersFromDb) {
                users.add(UserMapper.getInstance().entityToResponse(user));
            }

            return users;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public HttpStatus updateUser(UUID id, UserDto dto) {
        try {
            Optional<User> userFromDb = userRepository.findById(id);

            if(userFromDb.isPresent()) {
                User user = userFromDb.get();

                user.setLogin(dto.login());
                user.setPassword(passwordEncoder.encode(dto.password()));

                userRepository.save(user);

                return HttpStatus.OK;
            } else {
                return HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public HttpStatus patchUpdateUser(UUID id, UserDto dto) {
        try {
            Optional<User> userFromDb = userRepository.findById(id);

            if(userFromDb.isPresent()) {
                User user = userFromDb.get();

                if(dto.login() != null) {
                    user.setLogin(dto.login());
                }

                if(dto.password() != null) {
                    user.setPassword(passwordEncoder.encode(dto.password()));
                }

                userRepository.save(user);

                return HttpStatus.OK;
            } else {
                return HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public HttpStatus deleteUser(UUID id) {
        try {
            Optional<User> userFromDb = userRepository.findById(id);

            if(userFromDb.isPresent()) {
                userRepository.deleteById(id);

                return HttpStatus.OK;
            } else {
                return HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}