package com.hiro.questionnaires.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hiro.questionnaires.dto.AnswerDto;
import com.hiro.questionnaires.dto.QuestionnaireAnswerResponse;
import com.hiro.questionnaires.dto.QuestionnaireDto;
import com.hiro.questionnaires.dto.QuestionnaireRequest;
import com.hiro.questionnaires.dto.QuestionnaireResponse;
import com.hiro.questionnaires.service.QuestionnaireService;

@RestController
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;

    @PostMapping("/questionnaire")
    public ResponseEntity<Void> newQuestionnaire(@RequestBody QuestionnaireDto dto) {
        if(dto != null && dto.name() != null) {
            HttpStatus newQuestionnaire = questionnaireService.newQuestionnaire(dto);

            if(newQuestionnaire == HttpStatus.CREATED) {
                return ResponseEntity.status(201).build();
            } else if(newQuestionnaire == HttpStatus.CONFLICT) {
                return ResponseEntity.status(409).build();
            } else {
                return ResponseEntity.status(500).build();
            }
        } else {
            return ResponseEntity.status(422).build();
        }
    }

    @GetMapping("/questionnaires")
    public ResponseEntity<List<QuestionnaireResponse>> listQuestionnaires() {
        List<QuestionnaireResponse> questionnaires = questionnaireService.listQuestionnaires();

        if(questionnaires == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(questionnaires);
    }

    @PutMapping("/questionnaire/{id}")
    public ResponseEntity<Void> updateQuestionnaire(@PathVariable Integer id, @RequestBody QuestionnaireRequest request) {
        if(id != null && request != null) {
            HttpStatus updateQuestionnaire = questionnaireService.updateQuestionnaire(id, request);

            if(updateQuestionnaire == HttpStatus.OK) {
                return ResponseEntity.status(200).build();
            } else if(updateQuestionnaire == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(404).build();
            } else {
                return ResponseEntity.status(500).build();
            }
        } else {
            return ResponseEntity.status(422).build();
        }
    }

    @PatchMapping("/questionnaire/{id}")
    public ResponseEntity<Void> patchUpdateQuestionnaire(@PathVariable Integer id, @RequestBody QuestionnaireRequest request) {
        if(id != null  && request != null) {
            HttpStatus updateQuestionnaire = questionnaireService.patchUpdateQuestionnaire(id, request);

            if(updateQuestionnaire == HttpStatus.OK) {
                return ResponseEntity.status(200).build();
            } else if(updateQuestionnaire == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(404).build();
            } else {
                return ResponseEntity.status(422).build();
            }
        } else {
            return ResponseEntity.status(422).build();
        }
    }

    @DeleteMapping("/questionnaire/{id}")
    public ResponseEntity<Void> deleteQuestionnaire(@PathVariable Integer id) {
        if(id != null) {
            HttpStatus deleteQuestionnaire = questionnaireService.deleteQuestionnaire(id);

            if(deleteQuestionnaire == HttpStatus.OK) {
                return ResponseEntity.status(200).build();
            } else if(deleteQuestionnaire == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(404).build();
            } else {
                return ResponseEntity.status(500).build();
            }
        } else {
            return ResponseEntity.status(422).build();
        }
    }

    @PostMapping("/questionnaire/{id}/answer")
    public ResponseEntity<Void> newAnswer(@PathVariable Integer id, @RequestBody AnswerDto dto) {
        if(id != null && dto != null) {
            HttpStatus newAnswer = questionnaireService.newAnswer(id, dto);

            if(newAnswer == HttpStatus.CREATED) {
                return ResponseEntity.status(201).build();
            } else if(newAnswer == HttpStatus.CONFLICT) {
                return ResponseEntity.status(409).build();
            } else {
                return ResponseEntity.status(500).build();
            }
        } else {
            return ResponseEntity.status(422).build();
        }
    }

    @GetMapping("/questionnaire/{id}/answers")
    public ResponseEntity<List<QuestionnaireAnswerResponse>> listAnswer(Integer id) {
        List<QuestionnaireAnswerResponse> qaResponse = questionnaireService.listAnswer(id);

        if(qaResponse == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(2001).body(qaResponse);
    }
}