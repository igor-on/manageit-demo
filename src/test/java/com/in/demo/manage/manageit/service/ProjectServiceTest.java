package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.in.demo.manage.manageit.data.TestsData.generateSampleProject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private ProjectService service;

    @Test
    void testGetAllProjects() {
        var p1 = generateSampleProject();
        var p2 = generateSampleProject();
        when(repository.findAll()).thenReturn(List.of(p1, p2));

        List<Project> actual = service.getAllProjects();

        assertThat(actual)
                .hasSize(2)
                .containsExactly(p1, p2)
                .doesNotContainNull();

        assertThat(actual.get(0))
                .isEqualTo(p1);
    }

    @Test
    void testGetProjectById_WhenSuccess() throws DataNotFoundException {
        var p1 = generateSampleProject();
        long projectId = p1.getId();
        when(repository.findById(projectId)).thenReturn(Optional.of(p1));

        Project actual = service.getProjectById(projectId);

        assertThat(actual).isEqualTo(p1);
    }

    @Test
    void testGetProjectById_ShouldThrowException_WhenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Throwable throwable = Assertions.assertThrows(DataNotFoundException.class,
                () -> service.getProjectById(anyLong()));

        assertThat(throwable).isExactlyInstanceOf(DataNotFoundException.class);
    }

    @Test
    void testAddNewProject_WhenSuccess() throws DataNotFoundException {
        var project = generateSampleProject();
        project.setId(null);

        when(repository.save(project)).thenReturn(generateSampleProject());

        Project actual = service.addNewProject(project);

        assertNotNull(actual.getId());
        assertEquals(actual.getName(), project.getName());
        assertEquals(actual.getDescription(), project.getDescription());
        assertEquals(actual.getOwner(), project.getOwner());
    }

    @Test
    void testAddNewProject_ShouldThrowException_WhenIdIsNotNull() {
        var project = generateSampleProject();
        assertThrows(IllegalArgumentException.class, () -> service.addNewProject(project));
    }


    @Test
    void testDeleteProject_WhenSuccess() {
        var project = generateSampleProject();

        service.deleteProject(project.getId());

        verify(repository, times(1)).deleteById(project.getId());
    }

    @Test
    void testDeleteProject_WhenNotExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> service.getProjectById(10000L));
    }

    @Test
    void testUpdateProject_WhenSuccess() throws DataNotFoundException {
        var p1 = generateSampleProject();
        var p2 = generateSampleProject();

        when(repository.findById(p1.getId())).thenReturn(Optional.of(p1));
        p2 = service.updateProject(p1);

        assertEquals(p1.getId(), p2.getId());
        assertEquals(p1.getName(), p2.getName());
        assertEquals(p1.getDescription(), p2.getDescription());
        assertEquals(p1.getOwner(), p2.getOwner());
    }
}