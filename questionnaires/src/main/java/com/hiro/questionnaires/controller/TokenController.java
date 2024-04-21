package com.hiro.questionnaires.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hiro.questionnaires.dto.LoginRequest;
import com.hiro.questionnaires.dto.LoginResponse;
import com.hiro.questionnaires.service.TokenService;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        if(loginRequest != null && !loginRequest.login().isBlank() && !loginRequest.password().isBlank()) {
            LoginResponse loginResponse = tokenService.login(loginRequest);

            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }

    }
}