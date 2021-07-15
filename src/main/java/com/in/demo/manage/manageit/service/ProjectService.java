package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;

    public Project saveProject(Project project) {
        return repository.save(project);
    }
}
