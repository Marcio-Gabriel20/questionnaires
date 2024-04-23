package com.hiro.questionnaires.dto;

import java.util.List;

public record QuestionnaireDto(String name, String description, List<QuestionDto> questions) {
}