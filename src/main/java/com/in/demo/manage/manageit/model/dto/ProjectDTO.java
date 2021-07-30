package com.in.demo.manage.manageit.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectDTO {

    private final Long id;
    private final String name;
    private final String description;
    private final String ownerUsername;
}