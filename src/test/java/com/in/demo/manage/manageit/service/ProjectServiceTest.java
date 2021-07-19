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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository repository;

    @InjectMocks
    ProjectService service;

    @Test
    void findAllProjects() {
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
    void testGetProjectById() throws DataNotFoundException {
        Project p1 = generateSampleProject();
        when(repository.findById(1L)).thenReturn(Optional.of(p1));

        Project actual = service.getProjectById(1L);

        assertThat(actual).isEqualTo(p1);
    }

    @Test
    public void thatGetProjectByIdThrowsNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Throwable throwable = Assertions.assertThrows(DataNotFoundException.class, () -> service.getProjectById(anyLong()));

        assertThat(throwable).isExactlyInstanceOf(DataNotFoundException.class);
    }

    @Test
    void addNewProject() {
        var project = new Project();
        project.setDescription("ss");
        project.setName("ss");
        when(repository.save(any())).thenReturn(project);

        Project actual = service.addNewProject(project);

        assertEquals(actual.getName(), project.getName());
        assertEquals(actual.getDescription(), project.getDescription());
    }
//
//    @Test
//    void deleteProject() {
//    }
//
//    @Test
//    void updateProject() {
//    }
}
