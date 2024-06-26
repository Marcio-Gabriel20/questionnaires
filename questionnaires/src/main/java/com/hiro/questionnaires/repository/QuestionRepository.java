package com.hiro.questionnaires.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hiro.questionnaires.entity.Question;
import com.hiro.questionnaires.entity.Questionnaire;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByQuestionnaire(Questionnaire questionnaire);
}