package com.hiro.questionnaires.dto;

import java.util.Set;
import java.util.UUID;

import com.hiro.questionnaires.entity.Role;

public record UserResponse(UUID id, String login, Set<Role> roles) {
}