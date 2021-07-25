package com.in.demo.manage.manageit.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskDTO {

     public Long id;
     public String name;
     public String description;
     public String storyPoints;
     public String progress;
     public String priority;
     public Long sprintId;
}
