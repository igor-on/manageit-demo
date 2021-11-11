package com.in.demo.manage.manageit.mapper;

import com.in.demo.manage.manageit.model.Priority;
import com.in.demo.manage.manageit.model.Progress;
import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.model.Task;
import com.in.demo.manage.manageit.model.dto.TaskDTO;

public class TaskMapper {

    public static TaskDTO mapToTaskDTO(Task task) {

        Integer storyPoints = task.getStoryPoints();
        Progress progress = task.getProgress();
        Priority priority = task.getPriority();
        Sprint sprint = task.getSprint();

        return TaskDTO.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .storyPoints(storyPoints != null ? storyPoints.toString() : "")
                .progress(progress != null ? progress.toString() : "")
                .priority(priority != null ? priority.getDecimal().toString() : "")
                .sprintId(sprint != null ? sprint.getId() : null)
                .build();
    }
}
