package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.error.NotFoundException;
import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;

    public List<Project> findAllProjects() {
        return repository.findAll();
    }

    public Project getProjectById(Long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("There is no project with this " + id));
    }

    public Project addNewProject(Project project) {
        return repository.save(project);
    }

    public void deleteProject(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public Project updateProject(Project project) throws NotFoundException {
        Project updatedProject = getProjectById(project.getId());
        updatedProject.setName(project.getName());
        updatedProject.setDescription(project.getDescription());
        return updatedProject;
    }
}
