package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.model.*;
import com.in.demo.manage.manageit.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.in.demo.manage.manageit.service.data.DataForServicesTests.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService service;

    @Test
    void testFindAllTasks() {
        Task t1 = returnSampleTask();
        Task t2 = returnSampleTask();
        when(repository.findAll()).thenReturn(List.of(t1, t2));

        List<Task> actual = service.findAllTasks();

        assertThat(actual)
                .hasSize(2)
                .containsExactly(t1, t2)
                .doesNotContainNull();

        assertThat(actual.get(0))
                .isEqualTo(t1);
    }

    @Test
    void testGetTaskById_WhenSuccess() throws DataNotFoundException {
        Task t1 = returnSampleTask();
        long taskId = t1.getId();
        when(repository.findById(taskId)).thenReturn(Optional.of(t1));

        Task actual = service.getTaskById(taskId);

        assertThat(actual).isEqualTo(t1);
    }

    @Test
    void thatGetTaskById_ThrowsAnExceptionWhenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Throwable throwable = Assertions.assertThrows(DataNotFoundException.class,
                () -> service.getTaskById(anyLong()));

        assertThat(throwable).isExactlyInstanceOf(DataNotFoundException.class);
    }

    @Test
    void addNewTask() throws DataNotFoundException { // <--- todo ---// NPE or "HEJHEJHEJ" //-----------------------<<<
        Sprint s1 = returnSampleSprint();
        var task = new Task(100L, "task", "easy task", 5,
                Progress.IN_PROGRESS, Priority.ONE, s1);

        when(repository.save(any())).thenReturn(task);
        when(repository.findById(100L)).thenReturn(Optional.of(task));

        var taskById = service.getTaskById(100L);
        Task actual = service.addNewTask(taskById);

        assertNotNull(taskById);
        verify(repository, times(1)).findById(100L);

        assertEquals(actual.getId(), taskById.getId());
        assertEquals(actual.getName(), taskById.getName());
        assertEquals(actual.getDescription(), taskById.getDescription());
        assertEquals(actual.getStoryPoints(), taskById.getStoryPoints());
        assertEquals(actual.getProgress(), taskById.getProgress());
        assertEquals(actual.getPriority(), taskById.getPriority());
        assertEquals(actual.getSprint(), taskById.getSprint());
    }

    @Test
    void testDeleteSprint_WhenSuccess() { // <--- todo ----------------------------// enough? //-------------------<<<
        var task = returnSampleTask();
        //        when(repository.findById(anyLong())).thenReturn(Optional.of(task));

        service.deleteTask(task.getId());
    }

    @Test
    void testDeleteSprint_WhenNotExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> service.getTaskById(1500L));
    }

    @Test
    void testUpdateSprint() throws DataNotFoundException {
        var t1 = returnSampleTask();
        var t2 = new Task(100L, "task", "easy task", 5,
                Progress.IN_PROGRESS, Priority.ONE, returnSampleSprint());

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