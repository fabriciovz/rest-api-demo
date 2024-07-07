package com.fabribraguev.springboot.rest_api_demo.survey;


import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Predicate;


@Service
public class SurveryService {
    private static List<Survey> surveys= new ArrayList<>();

    static {
        Question question1 = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        Question question2 = new Question("Question2",
                "Fastest Growing Cloud Platform", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        Question question3 = new Question("Question3",
                "Most Popular DevOps Tool", Arrays.asList(
                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

        List<Question> questions = new ArrayList<>(Arrays.asList(question1,
                question2, question3));

        Survey survey = new Survey("Survey1", "My Favorite Survey",
                "Description of the Survey", questions);

        surveys.add(survey);
    }

    public List<Survey> retrieveAllSurveys() {
        return surveys;
    }

    public Survey retrieveSurveyById(String surveyId) {

        Predicate<? super Survey> predicate = survey -> survey.getId().equals(surveyId);
        Optional<Survey> optionalSurvey = surveys.stream().filter(predicate).findFirst();

        if (optionalSurvey.isEmpty()) return null;
        return optionalSurvey.get();
    }

    public List<Question> retrieveAllSurveyQuestions(String surveyId) {
        Survey survey = retrieveSurveyById(surveyId);
        if (survey==null) return null;
        return survey.getQuestions();
    }

    public Question retrieveSpecificSurveyQuestions(String surveyId, String questionId) {
        List<Question> surveyQuestions = retrieveAllSurveyQuestions(surveyId);

        if (surveyQuestions==null) return null;

        Predicate<? super Question> predicate = question -> question.getId().equals(questionId);
        Optional<Question> optionalQuestion = surveyQuestions.stream().filter(predicate).findFirst();

        if (optionalQuestion.isEmpty()) return null;

        return optionalQuestion.get();
    }

    public String addNewSurveyQuestion(String surveyId, Question question) {

        List<Question> surveyQuestions = retrieveAllSurveyQuestions(surveyId);

        question.setId(getRamdomId());

        surveyQuestions.add(question);

        return question.getId();
    }

    private static String getRamdomId() {
        SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(32, secureRandom).toString();
    }

    public String deleteSurveyQuestion(String surveyId, String questionId) {
        List<Question> surveyQuestions = retrieveAllSurveyQuestions(surveyId);

        if (surveyQuestions==null) return null;

        Predicate<? super Question> predicate = question -> question.getId().equals(questionId);

        boolean removed = surveyQuestions.removeIf(predicate);


        if (!removed) return null;

        return questionId;

    }

    public void updateSurveyQuestion(String surveyId, String questionId, Question question) {
        List<Question> surveyQuestions = retrieveAllSurveyQuestions(surveyId);
        surveyQuestions.removeIf(q -> q.getId().equals(questionId));
        surveyQuestions.add(question);
    }
}
