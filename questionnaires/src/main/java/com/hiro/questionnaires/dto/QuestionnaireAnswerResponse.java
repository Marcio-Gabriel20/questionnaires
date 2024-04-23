package com.hiro.questionnaires.dto;

public record QuestionnaireAnswerResponse(Integer questionnaire_id, String questionnaire_name, Integer question_id, String answer, String name_user) {
}