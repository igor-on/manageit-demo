package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.error.NotEnoughPointsException;
import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.model.Task;
import com.in.demo.manage.manageit.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final SprintService sprintService;
    private final TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById(Long id) throws DataNotFoundException {
        return repository.findById(id).orElseThrow(
                () -> new DataNotFoundException("There is no task with this id " + id));
    }

    @Transactional
    public void deleteTask(Long id) throws DataNotFoundException {
        Task foundTask = getTaskById(id);
        if (foundTask.getSprint() != null) {
            foundTask.getSprint().getTasks().remove(foundTask);
        }

        repository.deleteById(id);
    }

    public Task addNewTask(Task task) throws DataNotFoundException {
        if (task == null) {
            throw new DataNotFoundException("There is no task to add");
        }
        if (task.getId() != null) {
            throw new IllegalArgumentException("Id is auto-generated, cannot be created manually");
        }
        assignTaskToSprint(task, task.getSprint().getId());
        return repository.save(task);
    }

    @Transactional
    public void assignTaskToSprint(Task task, Long sprintId) throws DataNotFoundException {
        if (task.getSprint().getId() == null) {
            task.setSprint(null);
            return;
        }
        if (task.getId() != null) {
            task = getTaskById(task.getId());
        }
        System.out.println(task);

        Sprint relatedSprint = sprintService.getSprintById(sprintId);
        int pointsLeft = relatedSprint.getStoryPointsToSpend();
        if (pointsLeft - task.getStoryPoints() < 0) {
            throw new NotEnoughPointsException("There is not enough points in sprint");
        }
        relatedSprint.getTasks().add(task);
        relatedSprint.setStoryPointsToSpend(pointsLeft - task.getStoryPoints());
        task.setSprint(relatedSprint);
    }

    @Transactional
    public Task updateTask(Task task) throws DataNotFoundException {
        Task updatedTask = getTaskById(task.getId());
        updatedTask.setName(task.getName());
        updatedTask.setDescription(task.getDescription());

        //Liczy różnice punktów między nową a starą wersją zadania
        int pointsDifference = task.getStoryPoints() - updatedTask.getStoryPoints();
        updatedTask.setStoryPoints(task.getStoryPoints());

        Sprint relatedSprint = updatedTask.getSprint();
        if (relatedSprint != null) {
            //Odejmuje różnice od aktualnej ilosci punktow w sprincie i sprawdza poprawność wyniku
            int storyPointsInSprint = relatedSprint.getStoryPointsToSpend() - pointsDifference;
            if (storyPointsInSprint < 0 || storyPointsInSprint > 50) {
                throw new NotEnoughPointsException("There is not enough points in sprint");
            }
            relatedSprint.setStoryPointsToSpend(storyPointsInSprint);
        }

        updatedTask.setProgress(task.getProgress());
        updatedTask.setPriority(task.getPriority());
        return updatedTask;
    }
}
