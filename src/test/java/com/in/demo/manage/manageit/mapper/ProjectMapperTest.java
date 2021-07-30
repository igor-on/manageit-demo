package com.in.demo.manage.manageit.mapper;

import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.model.User;
import com.in.demo.manage.manageit.model.dto.ProjectDTO;
import org.junit.jupiter.api.Test;

import static com.in.demo.manage.manageit.data.TestsData.generateSampleUser;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectMapperTest {

    @Test
    void that_mapToProjectTO_worksCorrectly() {
        User owner = generateSampleUser();
        Project testProject = new Project(1L, "sample project",
                "project description", owner);

        ProjectDTO actual = ProjectMapper.mapToProjectDTO(testProject);

        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getName()).isEqualTo("sample project");
        assertThat(actual.getDescription()).isEqualTo("project description");
        assertThat(actual.getOwnerUsername()).isEqualTo("username");
    }
}