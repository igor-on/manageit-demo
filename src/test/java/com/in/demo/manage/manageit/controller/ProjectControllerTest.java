package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.service.ProjectService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.in.demo.manage.manageit.data.TestsData.generateSampleProject;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    private static final String PROJECTS_URI = "/api/v1/projects";

    @MockBean
    private ProjectService service;

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
    void shouldReturnListOfAllProjects() throws DataNotFoundException {
        var p1_temp = generateSampleProject();
        var p2_temp = generateSampleProject();
        p1_temp.setId(null);
        p2_temp.setId(null);

        Project t1 = service.addNewProject(p1_temp);
        Project t2 = service.addNewProject(p2_temp);
        List<Project> actual = service.getAllProjects();

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .when()
                .get(PROJECTS_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("actual", not(null))
                .body("actual.size()", is(2))
                .body("[0].id", equalTo(t1.getId())).body("[1].id", equalTo(t2.getId()))
                .body("[0].name", equalTo(t1.getName())).body("[1].name", equalTo(t2.getName()))
                .body("[0].description", equalTo(t1.getDescription()))
                .body("[1].description", equalTo(t2.getDescription()));
    }

    @Test
    void testGetProjectById_WhenSuccess() throws DataNotFoundException {
        Project project = service.getProjectById(generateSampleProject().getId());

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(String.valueOf(project.getId()))
                .log().body()
                .when()
                .get(PROJECTS_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", not(null))
                .body("id", equalTo(project.getId()))
                .body("name", equalTo(project.getName()))
                .body("description", equalTo(project.getDescription()));
    }

    @Test
    void testCreateProject_WhenSuccess() throws DataNotFoundException {
        var p1_temp = generateSampleProject();
        p1_temp.setId(null);

        Project p1 = service.addNewProject(p1_temp);

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(p1)
                .log().body()
                .when()
                .post(PROJECTS_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", not(null))
                .body("id", equalTo(p1.getId()))
                .body("name", equalTo(p1.getName()))
                .body("description", equalTo(p1.getDescription()));
    }

    @Test
    void testRemoveProject_WhenSuccess() throws DataNotFoundException {
        var p1 = generateSampleProject();

        service.deleteProject(p1.getId());
        Project testProject = service.getProjectById(p1.getId());

        assertNull(testProject);
        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(p1.getId())
                .log().body()
                .when()
                .delete(PROJECTS_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void testUpdateTask_WhenSuccess() throws DataNotFoundException {
        var p1 = service.getProjectById(generateSampleProject().getId());
        var p2 = service.getProjectById(generateSampleProject().getId());
        p2 = service.updateProject(p1);

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(p2)
                .log().body()
                .when()
                .put(PROJECTS_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", not(null))
                .body("id", equalTo(p1.getId()))
                .body("name", equalTo(p1.getName()))
                .body("description", equalTo(p1.getDescription()));
    }
}