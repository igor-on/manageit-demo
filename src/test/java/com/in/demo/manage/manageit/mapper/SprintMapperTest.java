package com.in.demo.manage.manageit.mapper;

import com.in.demo.manage.manageit.model.*;
import com.in.demo.manage.manageit.model.dto.SprintDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.in.demo.manage.manageit.data.TestsData.generateSampleUser;
import static org.assertj.core.api.Assertions.assertThat;

class SprintMapperTest {

    @Test
    void that_mapToSprintDTO_worksCorrectly() {
        List<User> usersList = new ArrayList<>();
        User user = generateSampleUser();
        long userId = user.getId();
        usersList.add(user);
        Sprint testSprint = new Sprint(1L, "testSprint",
                LocalDateTime.of(2021, 7, 10, 15, 30),
                LocalDateTime.of(2021, 7, 17, 15, 30),
                30, new ArrayList<>(), true, usersList);
        testSprint.getTasks().add(new Task(2L, "task1", "desc1", 4,
                Progress.TO_DO, Priority.TWO, testSprint));
        testSprint.getTasks().add(new Task(4L, "task2", "desc2", 2,
                Progress.TO_DO, Priority.ONE, testSprint));

        SprintDTO actual = SprintMapper.mapToSprintDTO(testSprint);

        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual).hasNoNullFieldsOrProperties();
        assertThat(actual.getName()).isEqualTo("testSprint");
        assertThat(actual.getStartDate()).isEqualTo("10 July 2021, 3:30 PM");
        assertThat(actual.getEndDate()).isEqualTo("17 July 2021, 3:30 PM");
        assertThat(actual.getStoryPointsToSpend()).isEqualTo("30");
        assertThat(actual.getTasksIds())
                .hasSize(2)
                .containsExactly(2L, 4L);
        assertThat(actual.isActive()).isTrue();
        assertThat(actual.getUsersIds())
                .hasSize(1)
                .containsExactly(userId);
    }
}