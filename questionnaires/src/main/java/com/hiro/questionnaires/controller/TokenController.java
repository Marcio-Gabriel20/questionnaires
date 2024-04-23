package com.hiro.questionnaires.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hiro.questionnaires.dto.LoginRequest;
import com.hiro.questionnaires.dto.LoginResponse;
import com.hiro.questionnaires.service.TokenService;

@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        if(loginRequest != null && !loginRequest.login().isBlank() && !loginRequest.password().isBlank()) {
            LoginResponse loginResponse = tokenService.login(loginRequest);

            return ResponseEntity.status(200).body(loginResponse);
        } else {
            return ResponseEntity.status(422).build();
        }

    }
}