package com.hiro.questionnaires.dto;

import java.util.Date;
import java.util.List;

public record QuestionnaireResponse(Integer id, Date createdDate, String name, String description, String creator, List<QuestionResponse> questions) {
}