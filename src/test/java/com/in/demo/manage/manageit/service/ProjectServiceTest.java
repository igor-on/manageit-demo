package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.error.NotFoundException;
import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProjectServiceTest {

    @InjectMocks
    ProjectService service;

//    @MockBean
    @Mock
    private ProjectRepository repository;

    @BeforeEach
    void init() {
        service = new ProjectService(repository);
    }

    @Test
    void findAllProjects() {
    }

    @Test
    void getProjectById_WhenNotExist() {
//        var project = new Project();
//        project.setId(1000L);
//        when(repository.findById(1000L)).thenReturn(Optional.of(project));
//        assertThrows(NotFoundException.class, () -> service.getProjectById().findById(project.getId()));
    }

    @Test
    void addNewProject() {
        var project = new Project();
        when(repository.save(project)).thenReturn(project);
        var id = service.addNewProject(project);
        assertEquals(project.getId(), id);
    }

    @Test
    void deleteProject() {
    }

    @Test
    void updateProject() {
    }
}