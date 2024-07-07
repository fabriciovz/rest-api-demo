package com.fabribraguev.springboot.rest_api_demo.survey;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class SurveyResource {

    private SurveryService surveryService;

    public SurveyResource(SurveryService surveryService) {
        this.surveryService = surveryService;
    }
    @RequestMapping("/surveys")
    public List<Survey> retrieveAllSurveys(){
        return surveryService.retrieveAllSurveys();
    }

    @RequestMapping("/surveys/{surveyId}")
    public Survey retrieveSurveyById(@PathVariable String surveyId){
        Survey survey = surveryService.retrieveSurveyById(surveyId);
        if (survey==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return survey;
    }
    @RequestMapping("/surveys/{surveyId}/questions")
    public List<Question> retrieveAllSurveyQuestions(@PathVariable String surveyId){
            List<Question> questions = surveryService.retrieveAllSurveyQuestions(surveyId);
        if (questions==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return questions;
    }
    @RequestMapping("/surveys/{surveyId}/questions/{questionId}")
    public Question retrieveSpecificSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId){
        Question question = surveryService.retrieveSpecificSurveyQuestions(surveyId,questionId);
        if (question==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return question;
    }

    @RequestMapping(value="/surveys/{surveyId}/questions", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question){

        String questionId= surveryService.addNewSurveyQuestion(surveyId,question);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{questionId}").buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location).build();

    }

    @RequestMapping(value = "/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId){
       surveryService.deleteSurveyQuestion(surveyId,questionId);

        return ResponseEntity.noContent().build();

    }

    @RequestMapping(value = "/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateSurveyQuestion(@PathVariable String surveyId,
                                                       @PathVariable String questionId,
                                                       @RequestBody Question question){
        surveryService.updateSurveyQuestion(surveyId,questionId,question);

        return ResponseEntity.noContent().build();

    }
}
