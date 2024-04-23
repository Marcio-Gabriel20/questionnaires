package com.hiro.questionnaires.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hiro.questionnaires.entity.Answer;
import com.hiro.questionnaires.entity.Question;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, UUID> {
    List<Answer> findByQuestion(Question question);
}