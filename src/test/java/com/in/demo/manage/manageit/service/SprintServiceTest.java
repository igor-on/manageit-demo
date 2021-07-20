package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.model.Task;
import com.in.demo.manage.manageit.repository.SprintRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.in.demo.manage.manageit.service.data.DataForServicesTests.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SprintServiceTest {

    @Mock
    private SprintRepository repository;

    @InjectMocks
    private SprintService service;

    @Test
    void testFindAllSprints() {
        Sprint s1 = returnSampleSprint();
        Sprint s2 = returnSampleSprint();
        when(repository.findAll()).thenReturn(List.of(s1, s2));

        List<Sprint> actual = service.findAllSprints();

        assertThat(actual)
                .hasSize(2)
                .containsExactly(s1, s2)
                .doesNotContainNull();

        assertThat(actual.get(0))
                .isEqualTo(s1);
    }

    @Test
    void testGetSprintById_WhenSuccess() throws DataNotFoundException {
        Sprint s1 = returnSampleSprint();
        long sprintId = s1.getId();
        when(repository.findById(sprintId)).thenReturn(Optional.of(s1));

        Sprint actual = service.getSprintById(sprintId);

        assertThat(actual).isEqualTo(s1);
    }

    @Test
    void thatGetSprintById_ThrowsAnExceptionWhenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Throwable throwable = Assertions.assertThrows(DataNotFoundException.class,
                () -> service.getSprintById(anyLong()));

        assertThat(throwable).isExactlyInstanceOf(DataNotFoundException.class);
    }

    @Test
    void addNewSprint() throws DataNotFoundException { // <--- todo ------------// fine? //-------------------------<<<
        Task t1 = returnSampleTask();
        Task t2 = returnSampleTask();
        var sprint = new Sprint(100L, "sprint",
                LocalDateTime.of(2021, 7, 20, 13, 30),
                LocalDateTime.of(2021, 7, 27, 13, 30),
                50, List.of(t1, t2));

        when(repository.save(sprint)).thenReturn(sprint);
        when(repository.findById(100L)).thenReturn(Optional.of(sprint));

        var sprintById = service.getSprintById(100L);
        Sprint actual = service.addNewSprint(sprintById);

        assertNotNull(sprintById);
        verify(repository, times(1)).findById(100L);

        assertEquals(actual.getId(), sprintById.getId());
        assertEquals(actual.getName(), sprintById.getName());
        assertEquals(actual.getStartDate(), sprintById.getStartDate());
        assertEquals(actual.getEndDate(), sprintById.getEndDate());
        assertEquals(actual.getStoryPointsToSpend(), sprintById.getStoryPointsToSpend());
        assertEquals(actual.getTasks(), sprintById.getTasks());
    }

    @Test
    void testDeleteSprint_WhenSuccess() { // <--- todo ----------------------------// enough? //--------------------<<<
        var sprint = returnSampleSprint();
//        when(repository.findById(anyLong())).thenReturn(Optional.of(sprint));

        service.deleteSprint(sprint.getId());
    }

    @Test
    void testDeleteSprint_WhenNotExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> service.getSprintById(1500L));
    }

    @Test
    void testUpdateSprint() throws DataNotFoundException {
        var s1 = returnSampleSprint();
        var s2 = new Sprint(100L, "sprint",
                LocalDateTime.of(2021, 7, 20, 13, 30),
                LocalDateTime.of(2021, 7, 27, 13, 30),
                50, List.of(returnSampleTask(), returnSampleTask()));

        when(repository.findById(s1.getId())).thenReturn(Optional.of(s1));
        s2 = service.updateSprint(s1);

        assertEquals(s1.getId(), s2.getId());
        assertEquals(s1.getName(), s2.getName());
        assertEquals(s1.getStartDate(), s2.getStartDate());
        assertEquals(s1.getEndDate(), s2.getEndDate());
        assertEquals(s1.getStoryPointsToSpend(), s2.getStoryPointsToSpend());
        assertEquals(s1.getTasks(), s2.getTasks());
    }
}