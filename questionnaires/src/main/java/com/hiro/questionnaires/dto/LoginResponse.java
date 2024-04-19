package com.hiro.questionnaires.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}