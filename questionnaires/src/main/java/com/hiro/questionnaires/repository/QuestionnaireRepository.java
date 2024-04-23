package com.hiro.questionnaires.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hiro.questionnaires.entity.Questionnaire;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Integer> {
    Optional<Questionnaire> findByName(String name);
}