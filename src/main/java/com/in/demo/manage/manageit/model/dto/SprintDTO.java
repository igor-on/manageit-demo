package com.in.demo.manage.manageit.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SprintDTO {

    public Long id;
    public String name;
    public String startDate;
    public String endDate;
    public String storyPointsToSpend;
    public List<Long> tasksIds;
}
