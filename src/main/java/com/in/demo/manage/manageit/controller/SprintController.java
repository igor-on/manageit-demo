package com.in.demo.manage.manageit.controller;

import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class SprintController {

    private final SprintService service;

    @PostMapping("/sprints")
                                                           // Nie wiem czy używać @Valid, fajnie się  łapie te wyjątki
                                                           // w GlobalErrorHandler
    public ResponseEntity<Sprint> createSprint(@RequestBody Sprint sprint) {
        Sprint createdSprint = service.saveSprint(sprint);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdSprint);
    }
}
