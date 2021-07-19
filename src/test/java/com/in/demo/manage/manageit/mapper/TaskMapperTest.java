package com.in.demo.manage.manageit.mapper;

import com.in.demo.manage.manageit.model.Priority;
import com.in.demo.manage.manageit.model.Progress;
import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.model.Task;
import com.in.demo.manage.manageit.model.dto.TaskDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class TaskMapperTest {

    @Test
    void that_mapToTaskDTO_worksCorrectly() {
        Sprint testSprint = new Sprint(1L, "testSprint", LocalDateTime.of(2021, 7, 10, 15, 30), LocalDateTime.of(2021, 7, 17, 15, 30), 30, new ArrayList<>());
        Task testTask = new Task(2L, "task1", "desc1", 4, Progress.TO_DO, Priority.TWO, testSprint);

        TaskDTO actual = TaskMapper.mapToTaskDTO(testTask);

        assertThat(actual.getId()).isEqualTo(2L);
        assertThat(actual.getPriority()).isEqualTo("2");
        assertThat(actual.getSprintId()).isEqualTo(1L);
    }

}
