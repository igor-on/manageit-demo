package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;

    public List<Project> getAllProjects() {
        return repository.findAll();
    }

    public Project getProjectById(Long id) throws DataNotFoundException {
        return repository.findById(id).orElseThrow(
                () -> new DataNotFoundException("There is no project with this id " + id));
    }

    public Project addNewProject(Project project) throws DataNotFoundException {
        if (project == null) {
            throw new DataNotFoundException("There is no project to add");
        }
        if (project.getId() != null) {
            throw new IllegalArgumentException("Id is auto-generated, cannot be created manually");
        }
        return repository.save(project);
    }

    public void deleteProject(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public Project updateProject(Project project) throws DataNotFoundException {
        Project updatedProject = getProjectById(project.getId());
        updatedProject.setName(project.getName());
        updatedProject.setDescription(project.getDescription());
        return updatedProject;
    }
}
