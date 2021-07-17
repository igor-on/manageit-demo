package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.model.Progress;
import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.model.Weight;
import com.in.demo.manage.manageit.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SprintService {

    private final SprintRepository repository;

    public List<Sprint> getAllSprints() {
        return repository.findAll();
    }

    public Sprint getSprintById(Long id) {
        return repository.findById(id).orElseThrow();  // TODO --- jakis konkretny new Error pewnie?
    }

    public Sprint addNewSprint(Sprint sprint) {
        return repository.save(sprint);
    }

    public void deleteSprint(Long id) {
        repository.deleteById(id);
    }

    public Sprint updateSprint(Long id, Sprint sprint) {
        Sprint updatedSprint = getSprintById(id);
        updatedSprint.setName(sprint.getName());
//        updatedSprint.setStartDate(sprint.getStartDate());  // TODO   --- zbędne?
//        updatedSprint.setEndDate(sprint.getEndDate());
//        updatedSprint.setTasks(sprint.getTasks());
        return updatedSprint;
    }
}