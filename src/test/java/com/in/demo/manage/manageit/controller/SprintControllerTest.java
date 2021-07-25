package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.model.Task;
import com.in.demo.manage.manageit.service.SprintService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.in.demo.manage.manageit.data.TestsData.generateSampleSprint;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@WebMvcTest(SprintController.class)
public class SprintControllerTest {

    private static final String SPRINTS_URI = "/api/v1/sprints";

    @MockBean
    private SprintService service;

    @Autowired
    private MockMvc mockMvc;

/*
    @Autowired
    private WebApplicationContext webApplicationContext;

    public void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        //        RestAssuredMockMvc.standaloneSetup(TaskController.class);
    }
*/

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldReturnListOfAllSprints() throws DataNotFoundException {
        var s1_temp = generateSampleSprint();
        var s2_temp = generateSampleSprint();
        s1_temp.setId(null);
        s2_temp.setId(null);

        Sprint s1 = service.addNewSprint(s1_temp);
        Sprint s2 = service.addNewSprint(s2_temp);
        List<Sprint> actual = service.getAllSprints();

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .when()
                .get(SPRINTS_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("actual", not(null))
                .body("actual.size()", is(2))
                .body("[0].id", equalTo(s1.getId())).body("[1].id", equalTo(s2.getId()))
                .body("[0].name", equalTo(s1.getName())).body("[1].name", equalTo(s2.getName()))
                .body("[0].startDate", equalTo(s1.getStartDate())).body("[1].startDate", equalTo(s2.getStartDate()))
                .body("[0].endDate", equalTo(s1.getEndDate())).body("[1].endDate", equalTo(s2.getEndDate()))
                .body("[0].storyPointsToSpend", equalTo(s1.getStoryPointsToSpend()))
                .body("[1].storyPointsToSpend", equalTo(s2.getStoryPointsToSpend()))
/* todo ??? */.body("[0].tasksIds", equalTo(s1.getTasks().stream().mapToLong(Task::getId)))
                .body("[1].tasksIds", equalTo(s2.getTasks().stream().mapToLong(Task::getId)));
    }

    @Test
    void testGetSprintById_WhenSuccess() throws DataNotFoundException {
        Sprint sprint = service.getSprintById(generateSampleSprint().getId());

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .queryParam(String.valueOf(sprint.getId()))
                .log().body()
                .when()
                .get(SPRINTS_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", not(null))
                .body("id", equalTo(sprint.getId()))
                .body("name", equalTo(sprint.getName()))
                .body("startDate", equalTo(sprint.getStartDate()))
                .body("endDate", equalTo(sprint.getEndDate()))
                .body("storyPointsToSpend", equalTo(sprint.getStoryPointsToSpend()))
/* todo ??? */.body("tasksIds", equalTo(sprint.getTasks().stream().mapToLong(Task::getId)));
    }

    @Test
    void testCreateSprint_WhenSuccess() throws DataNotFoundException {
        var s1_temp = generateSampleSprint();
        s1_temp.setId(null);

        Sprint sprint = service.addNewSprint(s1_temp);

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(sprint)
                .log().body()
                .when()
                .post(SPRINTS_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", not(null))
                .body("id", equalTo(sprint.getId()))
                .body("name", equalTo(sprint.getName()))
                .body("startDate", equalTo(sprint.getStartDate()))
                .body("endDate", equalTo(sprint.getEndDate()))
                .body("storyPointsToSpend", equalTo(sprint.getStoryPointsToSpend()))
/* todo ??? */.body("tasksIds", equalTo(sprint.getTasks().stream().mapToLong(Task::getId)));
    }

    @Test
    void testRemoveSprint_WhenSuccess() throws DataNotFoundException {
        var s1 = generateSampleSprint();

        service.deleteSprint(s1.getId());
        Sprint testSprint = service.getSprintById(s1.getId());

        assertNull(testSprint);
        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(s1.getId())
                .log().body()
                .when()
                .delete(SPRINTS_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void testUpdateSprint_WhenSuccess() throws DataNotFoundException {
        var s1 = service.getSprintById(generateSampleSprint().getId());
        var s2 = service.getSprintById(generateSampleSprint().getId());
        s2 = service.updateSprint(s1);

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(s2)
                .log().body()
                .when()
                .put(SPRINTS_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", not(null))
                .body("id", equalTo(s1.getId()))
                .body("name", equalTo(s1.getName()))
                .body("startDate", equalTo(s1.getStartDate()))
                .body("endDate", equalTo(s1.getEndDate()))
                .body("storyPointsToSpend", equalTo(s1.getStoryPointsToSpend()))
/* todo ??? */.body("tasksIds", equalTo(s1.getTasks().stream().mapToLong(Task::getId)));
    }
}