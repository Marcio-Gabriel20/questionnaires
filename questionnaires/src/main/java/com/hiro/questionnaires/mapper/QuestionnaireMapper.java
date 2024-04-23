package com.hiro.questionnaires.mapper;

import java.util.ArrayList;
import java.util.List;

import com.hiro.questionnaires.dto.QuestionResponse;
import com.hiro.questionnaires.dto.QuestionnaireResponse;
import com.hiro.questionnaires.entity.Question;
import com.hiro.questionnaires.entity.Questionnaire;

public class QuestionnaireMapper {
    private static QuestionnaireMapper instance;

    private QuestionnaireMapper() {
    }

    public static QuestionnaireMapper getInstance() {
        if(QuestionnaireMapper.instance == null) {
            QuestionnaireMapper.instance = new QuestionnaireMapper();
        }

        return QuestionnaireMapper.instance;
    }

    public QuestionnaireResponse entityToResponse(Questionnaire entity, List<Question> questionsEntity) {
        if(entity == null) {
            return null;
        }

        List<QuestionResponse> questions = new ArrayList<>();

        Integer newId = 0;

        for(Question question : questionsEntity) {
            newId++;
            QuestionResponse questionResponse = new QuestionResponse(newId, question.getDescription());
            questions.add(questionResponse);
        }

        return new QuestionnaireResponse(entity.getId(), entity.getCreatedDate(), entity.getName(), entity.getDescription(), entity.getCreator().getLogin(), questions);
    }
}