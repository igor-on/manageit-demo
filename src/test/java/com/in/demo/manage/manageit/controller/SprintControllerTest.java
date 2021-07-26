package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.mapper.SprintMapper;
import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.model.dto.SprintDTO;
import com.in.demo.manage.manageit.service.SprintService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.in.demo.manage.manageit.data.TestsData.generateSampleSprint;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest(SprintController.class)
public class SprintControllerTest {

    private static final String SPRINTS_URI = "/api/v1/sprints";

    @MockBean
    private SprintService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldReturnListOfAllSprints() {
        var s1 = generateSampleSprint();
        var s2 = generateSampleSprint();
        List<Sprint> sprintList = new ArrayList<>();
        sprintList.add(s1);
        sprintList.add(s2);
        Mockito.when(service.getAllSprints()).thenReturn(sprintList);
        SprintDTO sDTO1 = SprintMapper.mapToSprintDTO(sprintList.get(0));
        SprintDTO sDTO2 = SprintMapper.mapToSprintDTO(sprintList.get(1));
//        System.out.println(sDTO1.isActive());
//        System.out.println(sDTO2.isActive());
        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .get(SPRINTS_URI)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(".", notNullValue())
                .body("size()", is(2))
                .body("[0].id", equalTo(sDTO1.getId().intValue()))
                .body("[1].id", equalTo(sDTO2.getId().intValue()))
                .body("[0].name", equalTo(sDTO1.getName()))
                .body("[1].name", equalTo(sDTO2.getName()))
                .body("[0].startDate", equalTo(sDTO1.getStartDate()))
                .body("[1].startDate", equalTo(sDTO2.getStartDate()))
                .body("[0].endDate", equalTo(sDTO1.getEndDate()))
                .body("[1].endDate", equalTo(sDTO2.getEndDate()))
                .body("[0].storyPointsToSpend", equalTo(sDTO1.getStoryPointsToSpend()))
                .body("[1].storyPointsToSpend", equalTo(sDTO2.getStoryPointsToSpend()))
                .body("[0].tasksIds", equalTo(sDTO1.getTasksIds()))
                .body("[1].tasksIds", equalTo(sDTO2.getTasksIds()))
                .body("[0].active", equalTo(sDTO1.isActive()))
                .body("[1].active", equalTo(sDTO2.isActive()));
    }

    @Test
    void testGetSprintById_WhenSuccess() throws DataNotFoundException {
        Sprint sprint = generateSampleSprint();
        Mockito.when(service.getSprintById(1L)).thenReturn(sprint);

        SprintDTO sprintDTO = SprintMapper.mapToSprintDTO(sprint);

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .when().get(SPRINTS_URI + "/1")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", notNullValue())
                .body("id", equalTo(sprintDTO.getId().intValue()))
                .body("name", equalTo(sprintDTO.getName()))
                .body("startDate", equalTo(sprintDTO.getStartDate()))
                .body("endDate", equalTo(sprintDTO.getEndDate()))
                .body("storyPointsToSpend", equalTo(sprintDTO.getStoryPointsToSpend()))
                .body("tasksIds", equalTo(sprintDTO.getTasksIds()))
                .body("active", equalTo(sprintDTO.isActive()));
    }

    @Test
    void testCreateSprint_WhenSuccess() throws DataNotFoundException {
        var s1 = generateSampleSprint();
        s1.setId(null);
        Sprint sprint = generateSampleSprint();

        Mockito.when(service.addNewSprint(s1)).thenReturn(sprint);
        SprintDTO sprintDTO = SprintMapper.mapToSprintDTO(sprint);


        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(s1)
                .when().post(SPRINTS_URI)
                .then().assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("id", equalTo(sprintDTO.getId().intValue()))
                .body("name", equalTo(sprintDTO.getName()))
                .body("startDate", equalTo(sprintDTO.getStartDate()))
                .body("endDate", equalTo(sprintDTO.getEndDate()))
                .body("storyPointsToSpend", equalTo(sprintDTO.getStoryPointsToSpend()))
                .body("tasksIds", equalTo(sprintDTO.getTasksIds()))
                .body("active", equalTo(sprintDTO.isActive()));
    }

    @Test
    void testRemoveSprint_WhenSuccess() {
        Mockito.doNothing().when(service).deleteSprint(1L);
        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .when().delete(SPRINTS_URI + "/1")
                .then().assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
        Mockito.verify(service, Mockito.times(1)).deleteSprint(1L);
    }

    @Test
    void testUpdateSprint_WhenSuccess() throws DataNotFoundException {
        var s1 = generateSampleSprint();
        var s2 = generateSampleSprint();
        Mockito.when(service.updateSprint(s1)).thenReturn(s2);
        SprintDTO sprintDTO = SprintMapper.mapToSprintDTO(s2);

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(s1)
                .when().put(SPRINTS_URI)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", notNullValue())
                .body("id", equalTo(sprintDTO.getId().intValue()))
                .body("name", equalTo(sprintDTO.getName()))
                .body("startDate", equalTo(sprintDTO.getStartDate()))
                .body("endDate", equalTo(sprintDTO.getEndDate()))
                .body("storyPointsToSpend", equalTo(sprintDTO.getStoryPointsToSpend()))
                .body("tasksIds", equalTo(sprintDTO.getTasksIds()))
                .body("active", equalTo(sprintDTO.isActive()));
    }
}