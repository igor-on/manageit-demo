package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.error.UserNotFoundException;
import com.in.demo.manage.manageit.mapper.UserMapper;
import com.in.demo.manage.manageit.model.User;
import com.in.demo.manage.manageit.model.dto.UserDTO;
import com.in.demo.manage.manageit.service.UserService;
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

import static com.in.demo.manage.manageit.data.TestsData.generateSampleUser;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final String USERS_URI = "/api/v1/users";

    @MockBean
    private UserService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldReturnListOfAllUsers() {
        var u1 = generateSampleUser();
        var u2 = generateSampleUser();
        List<User> userList = new ArrayList<>();
        userList.add(u1);
        userList.add(u2);
        Mockito.when(service.getAllUsers()).thenReturn(userList);
        UserDTO uDTO1 = UserMapper.mapToUserDTO(userList.get(0));
        UserDTO uDTO2 = UserMapper.mapToUserDTO(userList.get(1));

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .get(USERS_URI)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(".", notNullValue())
                .body("size()", is(2))
                .body("[0].username", equalTo(uDTO1.getUsername()))
                .body("[1].username", equalTo(uDTO2.getUsername()))
                .body("[0].password", equalTo(uDTO1.getPassword()))
                .body("[1].password", equalTo(uDTO2.getPassword()))
                .body("[0].projectsIds", equalTo(uDTO1.getProjectsIds()))
                .body("[1].projectsIds", equalTo(uDTO2.getProjectsIds()));
    }

    @Test
    void testGetUserByUsername_WhenSuccess() throws UserNotFoundException {
        User user = generateSampleUser();
        Mockito.when(service.getUserByUsername("username")).thenReturn(user);

        UserDTO userDTO = UserMapper.mapToUserDTO(user);

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .when().get(USERS_URI + "/username")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("username", equalTo(userDTO.getUsername()))
                .body("password", equalTo(userDTO.getPassword()))
                .body("projectsIds", equalTo(userDTO.getProjectsIds()));
    }

    @Test
    void testRegisterNewUser_WhenSuccess() throws UserNotFoundException {
        var u1 = generateSampleUser();
        User user = generateSampleUser();

        Mockito.when(service.addNewUser(u1)).thenReturn(user);
        UserDTO userDTO = UserMapper.mapToUserDTO(user);


        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(u1)
                .when().post(USERS_URI)
                .then().assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("username", equalTo(userDTO.getUsername()))
                .body("password", equalTo(userDTO.getPassword()))
                .body("projectsIds", equalTo(userDTO.getProjectsIds()));
    }

    @Test
    void testRemoveUser_WhenSuccess() {
        Mockito.doNothing().when(service).deleteUser("username");
        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .when().delete(USERS_URI + "/username")
                .then().assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
        Mockito.verify(service, Mockito.times(1)).deleteUser("username");
    }

    @Test
    void testUpdateUser_WhenSuccess() throws UserNotFoundException {
        var u1 = generateSampleUser();
        var u2 = generateSampleUser();
        Mockito.when(service.updateUser(u1)).thenReturn(u2);
        UserDTO userDTO = UserMapper.mapToUserDTO(u2);

        given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(u1)
                .when().put(USERS_URI)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("username", equalTo(userDTO.getUsername()))
                .body("password", equalTo(userDTO.getPassword()))
                .body("projectsIds", equalTo(userDTO.getProjectsIds()));
    }
}
