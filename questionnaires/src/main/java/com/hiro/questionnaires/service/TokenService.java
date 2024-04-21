package com.hiro.questionnaires.service;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hiro.questionnaires.dto.LoginRequest;
import com.hiro.questionnaires.dto.LoginResponse;
import com.hiro.questionnaires.entity.Role;
import com.hiro.questionnaires.repository.UserRepository;

@Service
public class TokenService {
    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            var user = userRepository.findByLogin(loginRequest.login());

            if(user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
                throw new BadCredentialsException("Login or password is invalid!");

            }

            Instant now = Instant.now();
            Long expiresIn = 300L;

            String scopes = user.get().getRoles()
                    .stream()
                    .map(Role::getName)
                    .collect(Collectors.joining(" "));
            
            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("mybackend")
                    .subject(user.get().getUserId().toString())
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiresIn))
                    .claim("scopes", scopes)
                    .build();
            
            String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            return new LoginResponse(jwtValue, expiresIn);
        } catch (Exception e) {
            e.printStackTrace();

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}