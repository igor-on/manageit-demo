package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.error.DataNotFoundException;
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

    @CrossOrigin("http://localhost:4200")
    @GetMapping()
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> allProjects = service.getAllProjects();
        return ResponseEntity.ok(allProjects);
    }

    @CrossOrigin("http://localhost:4200")
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) throws DataNotFoundException {
        Project foundProject = service.getProjectById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(foundProject);
    }

    @CrossOrigin("http://localhost:4200")
    @PostMapping()
    public ResponseEntity<Project> createProject(@RequestBody Project project) throws DataNotFoundException {
        Project createdProject = service.addNewProject(project);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdProject);
    }

    @CrossOrigin("http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProject(@PathVariable Long id) {
        service.deleteProject(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @CrossOrigin("http://localhost:4200")
    @PutMapping()
    public ResponseEntity<Project> updateProject(@RequestBody Project project) throws DataNotFoundException {
        Project updatedProject = service.updateProject(project);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedProject);
    }
}
