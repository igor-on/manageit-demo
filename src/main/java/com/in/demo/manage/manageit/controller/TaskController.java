package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.error.NotFoundException;
import com.in.demo.manage.manageit.model.Task;
import com.in.demo.manage.manageit.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping()
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> allTasks = service.findAllTasks();
        return ResponseEntity.ok(allTasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) throws NotFoundException {
        Task foundTask = service.getTaskById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(foundTask);
    }

    @PostMapping()
    public ResponseEntity<Task> createTask(@RequestBody Task task) throws NotFoundException {
        Task createdTask = service.addNewTask(task);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeTask(@PathVariable Long id) {
        service.deleteTask(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@RequestBody Task task) throws NotFoundException {
        Task updatedTask = service.updateTask(task);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedTask);
    }
}
