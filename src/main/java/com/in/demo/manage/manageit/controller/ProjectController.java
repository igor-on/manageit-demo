package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService service;

    @GetMapping()
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> allProjects = service.findAllProjects();
        return ResponseEntity.ok(allProjects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project foundProject = service.getProjectById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(foundProject);
    }

    @PostMapping()
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project createdProject = service.addNewProject(project);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProject(@PathVariable Long id) {
        service.deleteProject(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping()
    public ResponseEntity<Project> updateProject(@RequestBody Project project) {
        Project updatedProject = service.updateProject(project);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedProject);
    }
}
