package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;

    public List<Project> getAllProjects() {
        return repository.findAll();
    }

    public Project getProjectById(Long id) {
        return repository.findById(id).orElseThrow();  // TODO --- jakis konkretny new Error pewnie?
    }

    public Project addNewProject(Project project) {
        return repository.save(project);
    }

    public void deleteProject(Long id) {
        repository.deleteById(id);
    }

    public Project updateProject(Long id, Project project) {
        Project updatedProject = getProjectById(id);
        updatedProject.setName(project.getName());
        updatedProject.setDescription(project.getDescription());
        return updatedProject;
    }
}
