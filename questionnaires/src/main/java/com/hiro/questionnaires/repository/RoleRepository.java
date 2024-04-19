package com.hiro.questionnaires.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hiro.questionnaires.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}