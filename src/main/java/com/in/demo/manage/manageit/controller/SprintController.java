package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.error.InvalidDataException;
import com.in.demo.manage.manageit.mapper.SprintMapper;
import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.model.dto.SprintDTO;
import com.in.demo.manage.manageit.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/v1/sprints")
@RequiredArgsConstructor
public class SprintController {

    private final SprintService service;

    @GetMapping()
    public ResponseEntity<List<SprintDTO>> getAllSprints() {
        List<Sprint> allSprints = service.getAllSprints();

        List<SprintDTO> dtos = allSprints.stream()
                .map(SprintMapper::mapToSprintDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SprintDTO> getSprintById(@PathVariable Long id) throws DataNotFoundException {
        Sprint foundSprint = service.getSprintById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SprintMapper.mapToSprintDTO(foundSprint));
    }

    @PostMapping()
    public ResponseEntity<SprintDTO> createSprint(@RequestBody Sprint sprint) throws DataNotFoundException {
        Sprint createdSprint = service.addNewSprint(sprint);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SprintMapper.mapToSprintDTO(createdSprint));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeSprint(@PathVariable Long id) throws DataNotFoundException {
        service.deleteSprint(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SprintDTO> startSprint(@PathVariable long id) throws DataNotFoundException, InvalidDataException {
        Sprint activated = service.changeToActive(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SprintMapper.mapToSprintDTO(activated));
    }

    @PutMapping("/finish/{id}")
    public ResponseEntity<SprintDTO> finishSprint(@PathVariable long id) throws DataNotFoundException, InvalidDataException {
        Sprint activated = service.changeToFinish(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SprintMapper.mapToSprintDTO(activated));
    }

    @PutMapping()
    public ResponseEntity<SprintDTO> updateSprint(@RequestBody Sprint sprint) throws DataNotFoundException {
        Sprint updatedSprint = service.updateSprint(sprint);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SprintMapper.mapToSprintDTO(updatedSprint));
    }
}
