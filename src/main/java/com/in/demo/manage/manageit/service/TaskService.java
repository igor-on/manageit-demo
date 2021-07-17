package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.model.Progress;
import com.in.demo.manage.manageit.model.Task;
import com.in.demo.manage.manageit.model.Weight;
import com.in.demo.manage.manageit.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final SprintService sprintService;
    private final TaskRepository repository;

    public List<Task> getAllTasks() {     // TODO --------------------zbędne??
        return repository.findAll();
    }

    // TODO --------------------zmieniłem z Set na List, nie sadze, ze moglyby sie powtorzyc,
    //  a wydaje mi sie, ze z listami wiecej mozna podzialac
    //  (w zaleznosci od potrzeb oczywiscie),
    //  no ale popraw mnie jesli miales jakis wyzszy cel w tym Secie
    public List<Task> getAllTasksFromSprint(Long sprintId) {
        return sprintService.getSprintById(sprintId).getTasks();
    }

    public Task getTaskById(Long id) {
        return repository.findById(id).orElseThrow();  // TODO --- jakis konkretny new Error pewnie?
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

    public Task addNewTask(Task task /*, Long sprintId*/) {
        return repository.save(task);
    }

    public Task updateTask(Long id, Task task) {
        Task updatedTask = getTaskById(id);
        updatedTask.setName(task.getName());
        updatedTask.setDescription(task.getDescription());
//        updatedTask.setStoryPoints(task.getStoryPoints()); // TODO   --- co rozni TaskWeight od StoryPoints?
//        updatedTask.setTaskWeight(task.getTaskWeight());
        return updatedTask; // TODO   ---  nie jestem pewien czy nie powinno byc tez repository.save.updatedTask, ale nie zdazylem sprawdzic
    }
}