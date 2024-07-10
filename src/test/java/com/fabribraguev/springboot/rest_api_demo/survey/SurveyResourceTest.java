package com.fabribraguev.springboot.rest_api_demo.survey;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = SurveyResource.class)
@AutoConfigureMockMvc(addFilters = false)
public class SurveyResourceTest {

    @MockBean
    private SurveryService surveryService;

    @Autowired
    private MockMvc mockMvc;

    private static String SPECIFIC_QUESTION_URL= "http://localhost:8080/surveys/Survey1/questions/Question1";

    private static String GENERIC_QUESTION_URL= "http://localhost:8080/surveys/Survey1/questions";

    @Test
    void retrieveSpecificSurveyQuestion_404Scenario() throws Exception {
        RequestBuilder requestBuilder =  MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(404,mvcResult.getResponse().getStatus());
    }

    @Test
    void retrieveSpecificSurveyQuestion_basicScenario() throws Exception {
        RequestBuilder requestBuilder =  MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
        Question question = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        when(surveryService.retrieveSpecificSurveyQuestions("Survey1","Question1")).thenReturn(question);
        String expectedResponse = "{\"id\":\"Question1\",\"description\":\"Most Popular Cloud Platform Today\",\"options\":[\"AWS\",\"Azure\",\"Google Cloud\",\"Oracle Cloud\"],\"correctAnswer\":\"AWS\"}";

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
        JSONAssert.assertEquals(expectedResponse,mvcResult.getResponse().getContentAsString(),false);
    }

    @Test
    void addNewSurveyQuestion_basicScenario() throws Exception {
        String requestBody = "{\n" +
                "  \"description\": \"Your favorite language\",\n" +
                "  \"options\": [\n" +
                "    \"Go\",\n" +
                "    \"Java\"\n" +
                "  ],\n" +
                "  \"correctAnswer\": \"Go\"\n" +
                "}";

        when(surveryService.addNewSurveyQuestion(anyString(),any())).thenReturn("SOME_ID");
        RequestBuilder requestBuilder =  MockMvcRequestBuilders.post(GENERIC_QUESTION_URL).
                accept(MediaType.APPLICATION_JSON).content(requestBody).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String locationHeader = mvcResult.getResponse().getHeader("Location");

        assertEquals(201,mvcResult.getResponse().getStatus());
        assertTrue(locationHeader.contains("/surveys/Survey1/questions/SOME_ID"));
    }
}
