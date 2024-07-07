package com.fabribraguev.springboot.rest_api_demo.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {
    private final String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";

    private final String GENERIC_QUESTION_URL = "/surveys/Survey1/questions/";


    @Autowired
    private TestRestTemplate template;
    @Test
    void retrieveSpecificSurveyQuestion_basicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

        String expectedResponse = "{\"id\":\"Question1\",\"description\":\"Most Popular Cloud Platform Today\",\"options\":[\"AWS\",\"Azure\",\"Google Cloud\",\"Oracle Cloud\"],\"correctAnswer\":\"AWS\"}";

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json",responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedResponse,responseEntity.getBody(),true);
    }

    @Test
    void retrieveAllSurveyQuestions_basicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = template.getForEntity(GENERIC_QUESTION_URL, String.class);

        String expectedResponse = "[{\"id\":\"Question1\",\"description\":\"Most Popular Cloud Platform Today\",\"options\":[\"AWS\",\"Azure\",\"Google Cloud\",\"Oracle Cloud\"],\"correctAnswer\":\"AWS\"},{\"id\":\"Question2\",\"description\":\"Fastest Growing Cloud Platform\",\"options\":[\"AWS\",\"Azure\",\"Google Cloud\",\"Oracle Cloud\"],\"correctAnswer\":\"Google Cloud\"},{\"id\":\"Question3\",\"description\":\"Most Popular DevOps Tool\",\"options\":[\"Kubernetes\",\"Docker\",\"Terraform\",\"Azure DevOps\"],\"correctAnswer\":\"Kubernetes\"}]";
        System.out.println(responseEntity.getBody());

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json",responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedResponse,responseEntity.getBody(),false);
    }

    @Test
    void addNewSurveyQuestion_basicScenario() {
        String requestBody = "{\n" +
                "  \"description\": \"Your favorite language\",\n" +
                "  \"options\": [\n" +
                "    \"Go\",\n" +
                "    \"Java\"\n" +
                "  ],\n" +
                "  \"correctAnswer\": \"Go\"\n" +
                "}";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type","application/json");

        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody,httpHeaders);

        ResponseEntity<String> responseEntity = template.exchange(GENERIC_QUESTION_URL, HttpMethod.POST, httpEntity, String.class);

        System.out.println(responseEntity.getBody());
        System.out.println(responseEntity.getHeaders());

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        String locationHeader = responseEntity.getHeaders().get("Location").get(0);
        assertTrue(locationHeader.contains("/surveys/Survey1/questions"));
        template.delete(locationHeader);
    }
}
