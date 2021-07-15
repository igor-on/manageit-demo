package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.model.Task;
import com.in.demo.manage.manageit.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final SprintService sprintService;
    private final TaskRepository repository;

    public Task saveTask(Task task) {
        return repository.save(task);
    }
}
