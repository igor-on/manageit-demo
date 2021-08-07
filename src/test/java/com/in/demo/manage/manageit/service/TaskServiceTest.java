package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.error.NotEnoughPointsException;
import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.model.Task;
import com.in.demo.manage.manageit.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.in.demo.manage.manageit.data.TestsData.generateSampleTask;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @Mock
    private SprintService sprintService;

    @InjectMocks
    private TaskService service;

    @Test
    void testGetAllTasks() {
        var t1 = generateSampleTask();
        var t2 = generateSampleTask();
        when(repository.findAll()).thenReturn(List.of(t1, t2));

        List<Task> actual = service.getAllTasks();

        assertThat(actual)
                .hasSize(2)
                .containsExactly(t1, t2)
                .doesNotContainNull();

        assertThat(actual.get(0))
                .isEqualTo(t1);
    }

    @Test
    void testGetTaskById_WhenSuccess() throws DataNotFoundException {
        var t1 = generateSampleTask();
        long taskId = t1.getId();
        when(repository.findById(taskId)).thenReturn(Optional.of(t1));

        Task actual = service.getTaskById(taskId);

        assertThat(actual).isEqualTo(t1);
    }

    @Test
    void testGetTaskById_ShouldThrowException_WhenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Throwable throwable = Assertions.assertThrows(DataNotFoundException.class,
                () -> service.getTaskById(anyLong()));

        assertThat(throwable).isExactlyInstanceOf(DataNotFoundException.class);
    }

    @Test
    void testAddNewTask_WhenSuccess() throws DataNotFoundException {
        var task = generateSampleTask();
        var task2 = generateSampleTask();
        Sprint sprint = task.getSprint();
        task2.setSprint(sprint);
        task.setId(null);

        when(repository.save(task)).thenReturn(task2);
        when(sprintService.getSprintById(task.getSprint().getId())).thenReturn(sprint);

        Task actual = service.addNewTask(task);

        assertNotNull(actual.getId());
        assertEquals(actual.getName(), task.getName());
        assertEquals(actual.getDescription(), task.getDescription());
        assertEquals(actual.getStoryPoints(), task.getStoryPoints());
        assertEquals(actual.getProgress(), task.getProgress());
        assertEquals(actual.getPriority(), task.getPriority());
        assertEquals(actual.getSprint().getId(), task.getSprint().getId());
    }

    @Test
    void testAddNewTask_ShouldThrowException_WhenTaskIsNull() {
        assertThrows(DataNotFoundException.class, () -> service.addNewTask(null));
    }

    @Test
    void testAddNewTask_ShouldThrowException_WhenIdIsNotNull() {
        var task = generateSampleTask();
        assertThrows(IllegalArgumentException.class, () -> service.addNewTask(task));
    }

    @Test
    void testAddNewTask_ShouldThrowException_WhenAssigningToSprintWhichDoesntHaveEnoughPointsToSpend()
            throws DataNotFoundException {
        var task = generateSampleTask();
        task.setId(null);
        var sprint = task.getSprint();
        sprint.setStoryPointsToSpend(0);

        when(sprintService.getSprintById(task.getSprint().getId())).thenReturn(sprint);

        assertThrows(NotEnoughPointsException.class, () -> service.addNewTask(task));
    }

    @Test
    void testDeleteSprint_WhenSuccess() throws DataNotFoundException {
        var task = generateSampleTask();

        when(repository.findById(task.getId())).thenReturn(Optional.of(task));
        service.deleteTask(task.getId());

        verify(repository, times(1)).deleteById(task.getId());
    }

    @Test
    void testDeleteSprint_WhenNotExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> service.getTaskById(10000L));
    }

    @Test
    void testUpdateSprint_WhenSuccess() throws DataNotFoundException {
        var t1 = generateSampleTask();
        var t2 = generateSampleTask();

        when(repository.findById(t1.getId())).thenReturn(Optional.of(t1));
        t2 = service.updateTask(t1);

        assertEquals(t1.getId(), t2.getId());
        assertEquals(t1.getName(), t2.getName());
        assertEquals(t1.getDescription(), t2.getDescription());
        assertEquals(t1.getStoryPoints(), t2.getStoryPoints());
        assertEquals(t1.getProgress(), t2.getProgress());
        assertEquals(t1.getPriority(), t2.getPriority());
        assertEquals(t1.getSprint(), t2.getSprint());
    }
}
