package com.in.demo.manage.manageit.mapper;

import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.model.Task;
import com.in.demo.manage.manageit.model.User;
import com.in.demo.manage.manageit.model.dto.SprintDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SprintMapper {

    public static SprintDTO mapToSprintDTO(Sprint sprint) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, h:mm a").localizedBy(Locale.ENGLISH);
        String stringStartDate = sprint.getStartDate() != null ? sprint.getStartDate().format(formatter) : null;
        String stringEndDate = sprint.getEndDate() != null ? sprint.getEndDate().format(formatter) : null;

        List<Long> taskIds = sprint.getTasks().stream()
                .map(Task::getId)
                .collect(Collectors.toList());

        List<String> usernames = sprint.getUsers().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        Integer storyPoints = sprint.getStoryPointsToSpend();

        return SprintDTO.builder()
                .id(sprint.getId())
                .name(sprint.getName())
                .startDate(stringStartDate)
                .endDate(stringEndDate)
                .storyPointsToSpend(storyPoints != null ? storyPoints.toString() : "")
                .tasksIds(taskIds)
                .isActive(sprint.isActive())
                .usernames(usernames)
                .build();
    }
}
