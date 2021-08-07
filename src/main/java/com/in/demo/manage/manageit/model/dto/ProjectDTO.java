package com.in.demo.manage.manageit.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProjectDTO {

    private final Long id;
    private final String name;
    private final String description;
    private final UserDTO owner;
    private final List<SprintDTO> sprints;
}