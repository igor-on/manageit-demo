package com.in.demo.manage.manageit.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskDTO {

     private final Long id;
     private final String name;
     private final String description;
     private final String storyPoints;
     private final String progress;
     private final String priority;
     private final Long sprintId;
}
