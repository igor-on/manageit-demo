package com.in.demo.manage.manageit.service;

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

    public Task getTaskById(Long id) {
        return repository.findById(id).orElseThrow();  // TODO --- jakis konkretny new Error pewnie?
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

    public Task addNewTask(Task task) {

        //Zadbanie o odpowiednia relacje ze Sprintem
        Sprint relatedSprint = sprintService.getSprintById(task.getSprint().getId());
        relatedSprint.getTasks().add(task);
        relatedSprint.setStoryPointsToSpend(relatedSprint.getStoryPointsToSpend() - task.getStoryPoints());
        task.setSprint(relatedSprint);

        return repository.save(task);
    }

    public Task updateTask(Task task) {
        Task updatedTask = getTaskById(task.getId());
        updatedTask.setName(task.getName());
        updatedTask.setDescription(task.getDescription());
        updatedTask.setStoryPoints(task.getStoryPoints());
        updatedTask.setPriority(task.getPriority());
        return updatedTask;
    }
}
