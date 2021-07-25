package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.model.Task;
import com.in.demo.manage.manageit.service.TaskService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.in.demo.manage.manageit.data.TestsData.generateSampleTask;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    private static final String TASKS_URI = "/api/v1/tasks";

    @MockBean
    private TaskService service;

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
    void shouldReturnListOfAllTasks() throws DataNotFoundException {
        var t1_temp = generateSampleTask();
        var t2_temp = generateSampleTask();
        t1_temp.setId(null);
        t2_temp.setId(null);

        Task t1 = service.addNewTask(t1_temp);
        Task t2 = service.addNewTask(t2_temp);
        List<Task> actual = service.getAllTasks();

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .when()
                .get(TASKS_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("actual", not(null))
                .body("actual.size()", is(2))
                .body("[0].id", equalTo(t1.getId())).body("[1].id", equalTo(t2.getId()))
                .body("[0].name", equalTo(t1.getName())).body("[1].name", equalTo(t2.getName()))
                .body("[0].description", equalTo(t1.getDescription())).body("[1].description", equalTo(t2.getDescription()))
                .body("[0].storyPoints", equalTo(t1.getStoryPoints())).body("[1].storyPoints", equalTo(t2.getStoryPoints()))
                .body("[0].priority", equalTo(t1.getPriority())).body("[1].priority", equalTo(t2.getPriority()))
                .body("[0].sprintId", equalTo(t1.getSprint().getId())).body("[1].sprintId", equalTo(t2.getSprint().getId()));
    }

    @Test
    void testGetTaskById_WhenSuccess() throws DataNotFoundException {
        Task task = service.getTaskById(generateSampleTask().getId());

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .queryParam(String.valueOf(task.getId()))
                .log().body()
                .when()
                .get(TASKS_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", not(null))
                .body("id", equalTo(task.getId()))
                .body("name", equalTo(task.getName()))
                .body("description", equalTo(task.getDescription()))
                .body("storyPoints", equalTo(task.getStoryPoints()))
                .body("priority", equalTo(task.getPriority()))
                .body("sprintId", equalTo(task.getSprint().getId()));
    }

    @Test
    void testCreateTask_WhenSuccess() throws DataNotFoundException {
        var t1_temp = generateSampleTask();
        t1_temp.setId(null);

        Task t1 = service.addNewTask(t1_temp);

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(t1)
                .log().body()
                .when()
                .post(TASKS_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", not(null))
                .body("id", equalTo(t1.getId()))
                .body("name", equalTo(t1.getName()))
                .body("description", equalTo(t1.getDescription()))
                .body("storyPoints", equalTo(t1.getStoryPoints()))
                .body("priority", equalTo(t1.getPriority()))
                .body("sprintId", equalTo(t1.getSprint().getId()));
    }

    @Test
    void testRemoveTask_WhenSuccess() throws DataNotFoundException {
        var t1 = generateSampleTask();

        service.deleteTask(t1.getId());
        Task testTask = service.getTaskById(t1.getId());

        assertNull(testTask);
        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(t1.getId())
                .log().body()
                .when()
                .delete(TASKS_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void testUpdateTask_WhenSuccess() throws DataNotFoundException {
        var t1 = service.getTaskById(generateSampleTask().getId());
        var t2 = service.getTaskById(generateSampleTask().getId());
        t2 = service.updateTask(t1);

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(t2)
                .log().body()
                .when()
                .put(TASKS_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", not(null))
                .body("id", equalTo(t1.getId()))
                .body("name", equalTo(t1.getName()))
                .body("description", equalTo(t1.getDescription()))
                .body("storyPoints", equalTo(t1.getStoryPoints()))
                .body("priority", equalTo(t1.getPriority()))
                .body("sprintId", equalTo(t1.getSprint().getId()));
    }
}