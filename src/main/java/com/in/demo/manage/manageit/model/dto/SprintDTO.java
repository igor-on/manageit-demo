package com.in.demo.manage.manageit.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SprintDTO {

    private final Long id;
    private final String name;
    private final String startDate;
    private final String endDate;
    private final String storyPointsToSpend;
    private final List<Long> tasksIds;
    private final boolean isActive;
    private final List<Long> usersIds;
}
