package com.hiro.questionnaires.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hiro.questionnaires.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByLogin(String login);
}