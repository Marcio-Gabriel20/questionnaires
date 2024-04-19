package com.hiro.questionnaires.controller;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
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

            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }

    }
}