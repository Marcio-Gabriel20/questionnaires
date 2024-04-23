package com.hiro.questionnaires.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hiro.questionnaires.dto.QuestionDto;
import com.hiro.questionnaires.dto.QuestionnaireDto;
import com.hiro.questionnaires.dto.QuestionnaireRequest;
import com.hiro.questionnaires.dto.QuestionnaireResponse;
import com.hiro.questionnaires.entity.Question;
import com.hiro.questionnaires.entity.Questionnaire;
import com.hiro.questionnaires.entity.User;
import com.hiro.questionnaires.mapper.QuestionnaireMapper;
import com.hiro.questionnaires.repository.QuestionRepository;
import com.hiro.questionnaires.repository.QuestionnaireRepository;
import com.hiro.questionnaires.repository.UserRepository;

@Service
public class QuestionnaireService {
    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public HttpStatus newQuestionnaire(QuestionnaireDto dto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            Optional<User> userFromDb = userRepository.findById(UUID.fromString(authentication.getName()));

            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setCreatedDate(new Date());
            questionnaire.setName(dto.name().toUpperCase());
            questionnaire.setDescription(dto.description());
            questionnaire.setCreator(userFromDb.get());

            Questionnaire questionnaireSave = questionnaireRepository.save(questionnaire);

            List<Question> questions = new ArrayList<>();

            for(QuestionDto questionDto : dto.questions()) {
                Question question = new Question();
                question.setDescription(questionDto.question());
                question.setQuestionnaire(questionnaireSave);

                questions.add(question);
            }

            questionRepository.saveAll(questions);

            return HttpStatus.CREATED;
        } catch (Exception e) {
            e.printStackTrace();

            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public List<QuestionnaireResponse> listQuestionnaires() {
        try {
            List<Questionnaire> questionnairesFromDb = questionnaireRepository.findAll();
            List<QuestionnaireResponse> questionnaires = new ArrayList<>();

            for(Questionnaire questionnaire : questionnairesFromDb) {
                List<Question> questionsFromDb = questionRepository.findByQuestionnaire(questionnaire);
                List<Question> questions = new ArrayList<>();

                if(questionsFromDb != null) {
                    for(Question question : questionsFromDb) {
                        if(question != null) {
                            questions.add(question);
                        }
                    }
                }
                
                questionnaires.add(QuestionnaireMapper.getInstance().entityToResponse(questionnaire, questions));
            }

            return questionnaires;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public HttpStatus updateQuestionnaire(Integer id, QuestionnaireRequest request) {
        try {
            Optional<Questionnaire> questionnaireFromDb = questionnaireRepository.findById(id);

            if(questionnaireFromDb.isPresent()) {
                Questionnaire questionnaire = questionnaireFromDb.get();
                questionnaire.setName(request.name());
                questionnaire.setDescription(request.description());

                questionnaireRepository.save(questionnaire);

                return HttpStatus.OK;
            } else {
                return HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public HttpStatus patchUpdateQuestionnaire(Integer id, QuestionnaireRequest request) {
        try {
            Optional<Questionnaire> questionnaireFromDb = questionnaireRepository.findById(id);

            if(questionnaireFromDb.isPresent()) {
                Questionnaire questionnaire = questionnaireFromDb.get();

                if(request.name() != null) {
                    questionnaire.setName(request.name());
                }

                if(request.description() != null) {
                    questionnaire.setDescription(request.description());
                }

                questionnaireRepository.save(questionnaire);

                return HttpStatus.OK;
            } else {
                return HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public HttpStatus deleteQuestionnaire(Integer id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Optional<Questionnaire> questionnaireFromDb = questionnaireRepository.findById(id);
            List<Question> questions = questionRepository.findByQuestionnaire(questionnaireFromDb.get());

            if(questionnaireFromDb.isPresent() && authentication.getName().equals(questionnaireFromDb.get().getCreator().getUserId().toString())) {
                questionRepository.deleteAll(questions);

                questionnaireRepository.deleteById(id);

                return HttpStatus.OK;
            } else {
                return HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}