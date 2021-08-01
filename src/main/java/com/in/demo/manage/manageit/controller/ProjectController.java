package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.mapper.ProjectMapper;
import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.model.dto.ProjectDTO;
import com.in.demo.manage.manageit.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService service;

    @GetMapping()
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<Project> allProjects = service.getAllProjects();

        List<ProjectDTO> dtos = allProjects.stream()
                .map(ProjectMapper::mapToProjectDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) throws DataNotFoundException {
        Project foundProject = service.getProjectById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ProjectMapper.mapToProjectDTO(foundProject));
    }

    @PostMapping()
    public ResponseEntity<ProjectDTO> createProject(@RequestBody Project project) throws DataNotFoundException {
        Project createdProject = service.addNewProject(project);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProjectMapper.mapToProjectDTO(createdProject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProject(@PathVariable Long id) {
        service.deleteProject(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping()
    public ResponseEntity<ProjectDTO> updateProject(@RequestBody Project project) throws DataNotFoundException {
        Project updatedProject = service.updateProject(project);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ProjectMapper.mapToProjectDTO(updatedProject));
    }
}
