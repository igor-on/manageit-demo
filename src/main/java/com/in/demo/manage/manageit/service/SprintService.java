package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SprintService {

    private final SprintRepository repository;

    public List<Sprint> findAllSprints() {
        return repository.findAll();
    }

    public Sprint getSprintById(Long id) throws DataNotFoundException {
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException("There is no sprint with this id " + id));
    }

    public Sprint addNewSprint(Sprint sprint) {
        return repository.save(sprint);
    }

    public void deleteSprint(Long id) {
        repository.deleteById(id);
    }

    public Sprint updateSprint(Sprint sprint) throws DataNotFoundException {
        Sprint updatedSprint = getSprintById(sprint.getId());
        updatedSprint.setName(sprint.getName());
        updatedSprint.setStartDate(sprint.getStartDate());
        updatedSprint.setEndDate(sprint.getEndDate());
        updatedSprint.setTasks(sprint.getTasks());
        return updatedSprint;
    }
}
