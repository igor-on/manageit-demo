package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.model.Task;
import com.in.demo.manage.manageit.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@RequestBody Task task ) {
        Task createdTask = service.saveTask(task);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTask);
    }
}
