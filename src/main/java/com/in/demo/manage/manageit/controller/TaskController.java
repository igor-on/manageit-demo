package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.mapper.TaskMapper;
import com.in.demo.manage.manageit.model.Task;
import com.in.demo.manage.manageit.model.dto.TaskDTO;
import com.in.demo.manage.manageit.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping()
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<Task> allTasks = service.findAllTasks();

        List<TaskDTO> dtos = allTasks.stream()
                .map(TaskMapper::mapToTaskDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Task foundTask = service.getTaskById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(TaskMapper.mapToTaskDTO(foundTask));
    }

    @PostMapping()
    public ResponseEntity<TaskDTO> createTask(@RequestBody Task task) {
        Task createdTask = service.addNewTask(task);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TaskMapper.mapToTaskDTO(createdTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeTask(@PathVariable Long id) {
        service.deleteTask(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@RequestBody Task task) {
        Task updatedTask = service.updateTask(task);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(TaskMapper.mapToTaskDTO(updatedTask));
    }
}
