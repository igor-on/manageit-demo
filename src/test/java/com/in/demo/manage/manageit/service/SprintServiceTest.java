package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.error.InvalidDataException;
import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.repository.SprintRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.in.demo.manage.manageit.data.TestsData.generateSampleSprint;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SprintServiceTest {

    @Mock
    private SprintRepository repository;
    @Mock
    private ProjectService projectService;

    @InjectMocks
    private SprintService service;

    @Test
    void testGetAllSprints() {
        var s1 = generateSampleSprint();
        var s2 = generateSampleSprint();
        when(repository.findAll()).thenReturn(List.of(s1, s2));

        List<Sprint> actual = service.getAllSprints();

        assertThat(actual)
                .hasSize(2)
                .containsExactly(s1, s2)
                .doesNotContainNull();

        assertThat(actual.get(0))
                .isEqualTo(s1);
    }

    @Test
    void testGetSprintById_WhenSuccess() throws DataNotFoundException {
        Sprint s1 = generateSampleSprint();

        when(repository.findById(s1.getId())).thenReturn(Optional.of(s1));

        Sprint actual = service.getSprintById(s1.getId());

        assertThat(actual).isEqualTo(s1);
    }

    @Test
    void testGetSprintById_ShouldThrowException_WhenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Throwable throwable = Assertions.assertThrows(DataNotFoundException.class,
                () -> service.getSprintById(anyLong()));

        assertThat(throwable).isExactlyInstanceOf(DataNotFoundException.class);
    }

    @Test
    void testAddNewSprint_WhenSuccess() throws DataNotFoundException {
        Sprint s1 = generateSampleSprint();
        s1.setId(null);
        Sprint s2 = generateSampleSprint();
        s2.getUsers().add(s1.getProject().getOwner());

        when(projectService.getProjectById(s1.getProject().getId())).thenReturn(s1.getProject());
        when(repository.save(s1)).thenReturn(s2);

        Sprint actual = service.addNewSprint(s1);

        assertNotNull(actual.getId());
        assertEquals(actual.getName(), s1.getName());
        assertEquals(actual.getStartDate(), s1.getStartDate());
        assertEquals(actual.getEndDate(), s1.getEndDate());
        assertEquals(actual.getStoryPointsToSpend(), s1.getStoryPointsToSpend());
        assertEquals(actual.getTasks(), s1.getTasks());
        assertThat(actual.getTasks()).isEmpty();
        assertEquals(actual.isActive(), s1.isActive());
        assertThat(actual.getUsers()).hasSize(1);
        assertEquals(actual.getUsers(), s1.getUsers());
    }

    @Test
    void testAddNewSprint_ShouldThrowException_WhenIdIsNotNull() {
        var sprint = generateSampleSprint();
        assertThrows(IllegalArgumentException.class, () -> service.addNewSprint(sprint));
    }

    @Test
    void testDeleteSprint_WhenSuccess() throws DataNotFoundException {
        var sprint = generateSampleSprint();

        when(repository.findById(sprint.getId())).thenReturn(Optional.of(sprint));
        service.deleteSprint(sprint.getId());

        verify(repository, times(1)).deleteById(sprint.getId());
    }

    @Test
    void testDeleteSprint_WhenNotExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> service.getSprintById(10000L));
    }

    @Test
    void testChangeToActive_WhenSuccess() throws DataNotFoundException, InvalidDataException {
        Sprint s1 = generateSampleSprint();

        when(repository.findById(s1.getId())).thenReturn(Optional.of(s1));
        Sprint actual = service.changeToActive(s1.getId());

        assertThat(actual.isActive()).isEqualTo(true);
    }

    @Test
    void testChangeToActive_WhenOtherSprintIsActive() {
        Sprint s1 = generateSampleSprint();
        Sprint s2 = generateSampleSprint();
        s2.setProject(s1.getProject());
        s2.setActive(true);
        Sprint s3 = generateSampleSprint();
        s3.setProject(s1.getProject());
        Project relatedProject = s1.getProject();
        relatedProject.getSprints().addAll(List.of(s1, s2, s3));

        when(repository.findById(s1.getId())).thenReturn(Optional.of(s1));

        Throwable throwable = Assertions.assertThrows(InvalidDataException.class, () -> service.changeToActive(s1.getId()));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidDataException.class)
                .hasMessage("Other sprint is already active");
    }

    @Test
    void testChangeToActive_WhenNoDataIsFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        Throwable throwable = Assertions.assertThrows(DataNotFoundException.class, () -> service.changeToActive(1L));

        assertThat(throwable)
                .isExactlyInstanceOf(DataNotFoundException.class)
                .hasMessage("There is no sprint with this id " + 1);
    }

    @Test
    void testChangeToFinish_WhenSuccess() throws DataNotFoundException, InvalidDataException {
        Sprint s1 = generateSampleSprint();
        s1.setActive(true);

        when(repository.findById(s1.getId())).thenReturn(Optional.of(s1));
        Sprint actual = service.changeToFinish(s1.getId());

        assertThat(actual.getId()).isEqualTo(s1.getId());
        assertThat(actual.isActive()).isEqualTo(false);
    }

    @Test
    void testChangeToFinish_WhenSprintIsNotActive() {
        Sprint s1 = generateSampleSprint();

        when(repository.findById(s1.getId())).thenReturn(Optional.of(s1));
        Throwable throwable = Assertions.assertThrows(InvalidDataException.class, () -> service.changeToFinish(s1.getId()));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidDataException.class)
                .hasMessage("This sprint isn't even active");
    }

    @Test
    void testChangeToFinish_WhenNoDataIsFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        Throwable throwable = Assertions.assertThrows(DataNotFoundException.class, () -> service.changeToFinish(1L));

        assertThat(throwable)
                .isExactlyInstanceOf(DataNotFoundException.class)
                .hasMessage("There is no sprint with this id " + 1);
    }

    @Test
    void testUpdateSprint_WhenSuccess() throws DataNotFoundException {
        var s1 = generateSampleSprint();

        when(repository.findById(s1.getId())).thenReturn(Optional.of(s1));
        Sprint actual = service.updateSprint(s1);

        assertEquals(s1.getId(), actual.getId());
        assertEquals(s1.getName(), actual.getName());
        assertEquals(s1.getStartDate(), actual.getStartDate());
        assertEquals(s1.getEndDate(), actual.getEndDate());
        assertEquals(s1.getStoryPointsToSpend(), actual.getStoryPointsToSpend());
        assertEquals(s1.getTasks(), actual.getTasks());
        assertEquals(s1.isActive(), actual.isActive());
    }
}
