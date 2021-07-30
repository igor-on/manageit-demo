package com.in.demo.manage.manageit.mapper;

import com.in.demo.manage.manageit.model.*;
import com.in.demo.manage.manageit.model.dto.TaskDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.in.demo.manage.manageit.data.TestsData.generateSampleProject;
import static com.in.demo.manage.manageit.data.TestsData.generateSampleUser;
import static org.assertj.core.api.Assertions.assertThat;

class TaskMapperTest {

    @Test
    void that_mapToTaskDTO_worksCorrectly() {
        List<User> usersList = new ArrayList<>();
        usersList.add(generateSampleUser());
        Sprint testSprint = new Sprint(1L, "testSprint",
                LocalDateTime.of(2021, 7, 10, 15, 30),
                LocalDateTime.of(2021, 7, 17, 15, 30),
                30, new ArrayList<>(), true, usersList, generateSampleProject());
        Task testTask = new Task(2L, "task1", "desc1", 4,
                Progress.TO_DO, Priority.TWO, testSprint);

        TaskDTO actual = TaskMapper.mapToTaskDTO(testTask);

        assertThat(actual.getId()).isEqualTo(2L);
        assertThat(actual.getName()).isEqualTo("task1");
        assertThat(actual.getDescription()).isEqualTo("desc1");
        assertThat(actual.getStoryPoints()).isEqualTo("4");
        assertThat(actual.getProgress()).isEqualTo("TO_DO");
        assertThat(actual.getPriority()).isEqualTo("1");
        assertThat(actual.getSprintId()).isEqualTo(1L);
    }
}
