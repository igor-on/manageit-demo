package com.in.demo.manage.manageit.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SprintDTO {

    private Long id;
    private String name;
    private String startDate;
    private String endDate;
    private String storyPointsToSpend;
    private List<Long> tasksIds;
}
