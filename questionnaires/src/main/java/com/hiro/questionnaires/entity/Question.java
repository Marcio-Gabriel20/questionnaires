package com.hiro.questionnaires.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_QUESTION")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUESTION_ID")
    private Integer id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "QUESTIONNAIRE_ID", nullable = false)
    private Questionnaire questionnaire;

    public Question() {
    }

    public Question(Integer id, String description, Questionnaire questionnaire) {
        this.id = id;
        this.description = description;
        this.questionnaire = questionnaire;
    }

    public Question(String description, Questionnaire questionnaire) {
        this.description = description;
        this.questionnaire = questionnaire;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }
}