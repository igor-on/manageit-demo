package com.in.demo.manage.manageit.mapper;

import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.model.User;
import com.in.demo.manage.manageit.model.dto.ProjectDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.in.demo.manage.manageit.data.TestsData.generateSampleSprint;
import static com.in.demo.manage.manageit.data.TestsData.generateSampleUser;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectMapperTest {

    @Test
    void that_mapToProjectTO_worksCorrectly() {
        Sprint s1 = generateSampleSprint();
        Sprint s2 = generateSampleSprint();
        List<Sprint> sprintList = List.of(s1, s2);

        User owner = generateSampleUser();
        Project testProject = new Project(1L, "sample project",
                "project description", owner, sprintList);

        ProjectDTO actual = ProjectMapper.mapToProjectDTO(testProject);

        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getName()).isEqualTo("sample project");
        assertThat(actual.getDescription()).isEqualTo("project description");
        assertThat(actual.getOwner()).isEqualTo(owner);
    }
}
