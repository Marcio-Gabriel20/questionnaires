package com.hiro.questionnaires.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_QUESTIONNAIRE")
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUESTIONNAIRE_ID")
    private Integer id;

    private Date createdDate;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User creator;

    public Questionnaire() {
    }

    public Questionnaire(Integer id, Date createdDate, String name, String description, User creator) {
        this.id = id;
        this.createdDate = createdDate;
        this.name = name;
        this.description = description;
        this.creator = creator;
    }

    public Questionnaire(Date createdDate, String name, String description, User creator) {
        this.createdDate = createdDate;
        this.name = name;
        this.description = description;
        this.creator = creator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    
}