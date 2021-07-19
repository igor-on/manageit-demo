package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/sprints")
@RequiredArgsConstructor
public class SprintController {

    private final SprintService service;

    @GetMapping()
    public ResponseEntity<List<Sprint>> getAllSprints() {
        List<Sprint> allSprints = service.findAllSprints();
        return ResponseEntity.ok(allSprints);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sprint> getSprintById(@PathVariable Long id) throws DataNotFoundException {
        Sprint foundSprint = service.getSprintById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(foundSprint);
    }

    @PostMapping()
    public ResponseEntity<Sprint> createSprint(@RequestBody Sprint sprint) {
        Sprint createdSprint = service.addNewSprint(sprint);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdSprint);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeSprint(@PathVariable Long id) {
        service.deleteSprint(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sprint> updateSprint(@RequestBody Sprint sprint) throws DataNotFoundException {
        Sprint updatedSprint = service.updateSprint(sprint);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedSprint);
    }
}
