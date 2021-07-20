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

import static com.in.demo.manage.manageit.service.data.DataForServicesTests.generateSampleProject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private ProjectService service;

    @Test
    void testFindAllProjects() {
        Project p1 = generateSampleProject();
        Project p2 = generateSampleProject();
        when(repository.findAll()).thenReturn(List.of(p1, p2));

        List<Project> actual = service.findAllProjects();

        assertThat(actual)
                .hasSize(2)
                .containsExactly(p1, p2)
                .doesNotContainNull();

        assertThat(actual.get(0))
                .isEqualTo(p1);
    }

    @Test
    void testGetProjectById_WhenSuccess() throws DataNotFoundException {
        Project p1 = generateSampleProject();
        long projectId = p1.getId();
        when(repository.findById(projectId)).thenReturn(Optional.of(p1));

        Project actual = service.getProjectById(projectId);

        assertThat(actual).isEqualTo(p1);
    }

    @Test
    void thatGetProjectById_ThrowsAnExceptionWhenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Throwable throwable = Assertions.assertThrows(DataNotFoundException.class,
                () -> service.getProjectById(anyLong()));

        assertThat(throwable).isExactlyInstanceOf(DataNotFoundException.class);
    }

    @Test
    void testAddNewProject() throws DataNotFoundException { // <--- todo ------------// fine? //--------------------<<<
        var project = new Project(100L, "project", "best project");

        when(repository.save(any())).thenReturn(project);
        when(repository.findById(100L)).thenReturn(Optional.of(project));

        var projectById = service.getProjectById(100L);
        assertNotNull(projectById);

        Project actual = service.addNewProject(projectById);

        assertEquals(actual.getId(), projectById.getId());
        assertEquals(actual.getName(), projectById.getName());
        assertEquals(actual.getDescription(), projectById.getDescription());
    }

    @Test
    void testDeleteProject_WhenSuccess() { // <--- todo ----------------------------// enough? //-------------------<<<
        var project = new Project(100L, "project", "best project");
//        when(repository.findById(anyLong())).thenReturn(Optional.of(project));

        service.deleteProject(project.getId());
    }

    @Test
    void testDeleteProject_WhenNotExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> service.getProjectById(1500L));
    }

    @Test
    void testUpdateProject() throws DataNotFoundException {
        var p1 = generateSampleProject();
        var p2 = generateSampleProject();

        when(repository.findById(p1.getId())).thenReturn(Optional.of(p1));
        p2 = service.updateProject(p1);

        assertEquals(p1.getId(), p2.getId());
        assertEquals(p1.getName(), p2.getName());
        assertEquals(p1.getDescription(), p2.getDescription());
    }
}