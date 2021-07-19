package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.model.Task;
import com.in.demo.manage.manageit.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final SprintService sprintService;
    private final TaskRepository repository;

    public List<Task> findAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById(Long id) throws DataNotFoundException {
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException("There is no task with this id " + id));
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

    public Task addNewTask(Task task) throws DataNotFoundException {


        if(task.getId() != null) {
            throw new IllegalArgumentException("HEJHEJEH");
        }

        //Zadbanie o odpowiednia relacje ze Sprintem
        Sprint relatedSprint = sprintService.getSprintById(task.getSprint().getId());
        relatedSprint.getTasks().add(task);
        task.setSprint(relatedSprint);

        return repository.save(task);
    }

    public Task updateTask(Task task) throws DataNotFoundException {
        Task updatedTask = getTaskById(task.getId());
        updatedTask.setName(task.getName());
        updatedTask.setDescription(task.getDescription());
        updatedTask.setStoryPoints(task.getStoryPoints());
        updatedTask.setPriority(task.getPriority());
        return updatedTask;
    }
}
