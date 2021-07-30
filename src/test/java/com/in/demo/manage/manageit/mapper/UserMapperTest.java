package com.in.demo.manage.manageit.mapper;

import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.model.User;
import com.in.demo.manage.manageit.model.dto.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.in.demo.manage.manageit.data.TestsData.generateSampleProject;
import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    @Test
    void that_mapToUserDTO_worksCorrectly() {
        List<Project> projectsList = new ArrayList<>();
        var p1 = generateSampleProject();
        long projectId = p1.getId();
        projectsList.add(p1);

        User testUser = new User(1L, "username", "password", projectsList, new ArrayList<>());

        UserDTO actual = UserMapper.mapToUserDTO(testUser);


        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getUsername()).isEqualTo("username");
        assertThat(actual.getPassword()).isEqualTo("password");
    }
}
