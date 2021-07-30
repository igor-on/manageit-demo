package com.in.demo.manage.manageit.mapper;

import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.model.dto.ProjectDTO;
import com.in.demo.manage.manageit.model.dto.SprintDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectMapper {

    public static ProjectDTO mapToProjectDTO(Project project) {

        List<SprintDTO> mappedSprints = project.getSprints().stream()
                .map(SprintMapper::mapToSprintDTO)
                .collect(Collectors.toList());

        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .owner(UserMapper.mapToUserDTO(project.getOwner()))
                .sprints(mappedSprints)
                .build();
    }
}
