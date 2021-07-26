package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.mapper.TaskMapper;
import com.in.demo.manage.manageit.model.Task;
import com.in.demo.manage.manageit.model.dto.TaskDTO;
import com.in.demo.manage.manageit.service.TaskService;
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

import static com.in.demo.manage.manageit.data.TestsData.generateSampleTask;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    private static final String TASKS_URI = "/api/v1/tasks";

    @MockBean
    private TaskService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldReturnListOfAllTasks() {
        var t1 = generateSampleTask();
        var t2 = generateSampleTask();
        List<Task> taskList = new ArrayList<>();
        taskList.add(t1);
        taskList.add(t2);
        Mockito.when(service.getAllTasks()).thenReturn(taskList);
        TaskDTO tDTO1 = TaskMapper.mapToTaskDTO(taskList.get(0));
        TaskDTO tDTO2 = TaskMapper.mapToTaskDTO(taskList.get(1));

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .when().get(TASKS_URI)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(".", notNullValue())
                .body("size()", is(2))
                .body("[0].id", equalTo(tDTO1.getId().intValue()))
                .body("[1].id", equalTo(tDTO2.getId().intValue()))
                .body("[0].name", equalTo(tDTO1.getName()))
                .body("[1].name", equalTo(tDTO2.getName()))
                .body("[0].description", equalTo(tDTO1.getDescription()))
                .body("[1].description", equalTo(tDTO2.getDescription()))
                .body("[0].storyPoints", equalTo(tDTO1.getStoryPoints()))
                .body("[1].storyPoints", equalTo(tDTO2.getStoryPoints()))
                .body("[0].progress", equalTo(tDTO1.getProgress()))
                .body("[1].progress", equalTo(tDTO2.getProgress()))
                .body("[0].priority", equalTo(tDTO1.getPriority()))
                .body("[1].priority", equalTo(tDTO2.getPriority()))
                .body("[0].sprintId", equalTo(tDTO1.getSprintId().intValue()))
                .body("[1].sprintId", equalTo(tDTO2.getSprintId().intValue()));
    }

    @Test
    void testGetTaskById_WhenSuccess() throws DataNotFoundException {
        Task task = generateSampleTask();
        Mockito.when(service.getTaskById(1L)).thenReturn(task);

        TaskDTO taskDTO = TaskMapper.mapToTaskDTO(task);

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .when().get(TASKS_URI + "/1")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", notNullValue())
                .body("id", equalTo(taskDTO.getId().intValue()))
                .body("name", equalTo(taskDTO.getName()))
                .body("description", equalTo(taskDTO.getDescription()))
                .body("storyPoints", equalTo(taskDTO.getStoryPoints()))
                .body("progress", equalTo(taskDTO.getProgress()))
                .body("priority", equalTo(taskDTO.getPriority()))
                .body("sprintId", equalTo(taskDTO.getSprintId().intValue()));
    }

    @Test
    void testCreateTask_WhenSuccess() throws DataNotFoundException {
        var t1 = generateSampleTask();
        t1.setId(null);
        Task task = generateSampleTask();

        Mockito.when(service.addNewTask(t1)).thenReturn(task);
        TaskDTO taskDTO = TaskMapper.mapToTaskDTO(task);

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(t1)
                .when().post(TASKS_URI)
                .then().assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("id", equalTo(taskDTO.getId().intValue()))
                .body("name", equalTo(taskDTO.getName()))
                .body("description", equalTo(taskDTO.getDescription()))
                .body("storyPoints", equalTo(taskDTO.getStoryPoints()))
                .body("progress", equalTo(taskDTO.getProgress()))
                .body("priority", equalTo(taskDTO.getPriority()))
                .body("sprintId", equalTo(taskDTO.getSprintId().intValue()));
    }

    @Test
    void testRemoveTask_WhenSuccess() {
        Mockito.doNothing().when(service).deleteTask(1L);
        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .when().delete(TASKS_URI + "/1")
                .then().assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
        Mockito.verify(service, Mockito.times(1)).deleteTask(1L);
    }

    @Test
    void testUpdateTask_WhenSuccess() throws DataNotFoundException {
        var t1 = generateSampleTask();
        var t2 = generateSampleTask();
        Mockito.when(service.updateTask(t1)).thenReturn(t2);
        TaskDTO taskDTO = TaskMapper.mapToTaskDTO(t2);

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(t1)
                .when().put(TASKS_URI)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", notNullValue())
                .body("id", equalTo(taskDTO.getId().intValue()))
                .body("name", equalTo(taskDTO.getName()))
                .body("description", equalTo(taskDTO.getDescription()))
                .body("storyPoints", equalTo(taskDTO.getStoryPoints()))
                .body("progress", equalTo(taskDTO.getProgress()))
                .body("priority", equalTo(taskDTO.getPriority()))
                .body("sprintId", equalTo(taskDTO.getSprintId().intValue()));
    }
}