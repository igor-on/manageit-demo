package com.in.demo.manage.manageit.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskDTO {

     private Long id;
     private String name;
     private String description;
     private String storyPoints;
     private String progress;
     private String priority;
     private Long sprintId;
}
