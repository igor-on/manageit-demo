package com.in.demo.manage.manageit.mapper;

import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.model.Task;
import com.in.demo.manage.manageit.model.dto.SprintDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SprintMapper {

    public static SprintDTO mapToSprintDTO(Sprint sprint) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, h:mm a").localizedBy(Locale.ENGLISH);
        String stringStartDate = sprint.getStartDate().format(formatter);
        String stringEndDate = sprint.getEndDate().format(formatter);

        List<Long> taskIds = sprint.getTasks().stream()
                .map(Task::getId)
                .collect(Collectors.toList());

        Integer storyPoints = sprint.getStoryPointsToSpend();

        return SprintDTO.builder()
                .id(sprint.getId())
                .name(sprint.getName())
                .startDate(stringStartDate)
                .endDate(stringEndDate)
                .storyPointsToSpend(storyPoints != null ? storyPoints.toString() : "")
                .tasksIds(taskIds)
                .build();
    }
}
