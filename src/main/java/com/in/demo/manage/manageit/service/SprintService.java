package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.error.InvalidDataException;
import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SprintService {

    private final ProjectService projectService;
    private final SprintRepository repository;

    public List<Sprint> getAllSprints() {
        return repository.findAll();
    }

    public Sprint getSprintById(Long id) throws DataNotFoundException {
        return repository.findById(id).orElseThrow(
                () -> new DataNotFoundException("There is no sprint with this id " + id));
    }

    public Sprint addNewSprint(Sprint sprint) throws DataNotFoundException {
        if (sprint == null) {
            throw new DataNotFoundException("There is no sprint to add");
        }
        if (sprint.getId() != null) {
            throw new IllegalArgumentException("Id is auto-generated, cannot be created manually");
        }

        // todo ---------- sprawdzic z entity czy spoko
//        sprint.setProject(projectService.getProjectById(sprint.getProject().getId()));

        Project relatedProject = projectService.getProjectById(sprint.getProject().getId());
        relatedProject.getSprints().add(sprint);
        sprint.setProject(relatedProject);
        sprint.getUsers().add(relatedProject.getOwner());
        relatedProject.getOwner().getSprints().add(sprint);

        return repository.save(sprint);
    }

    public void deleteSprint(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public Sprint changeToActive(long id) throws DataNotFoundException, InvalidDataException {
        Sprint sprintToActivate = getSprintById(id);

        long activatedSprints = getAllSprints().stream().filter(Sprint::isActive).count();
        if (activatedSprints >= 1) {
            throw new InvalidDataException("Other sprint is already active");
        }

        sprintToActivate.setActive(true);
        return sprintToActivate;
    }

    @Transactional
    public Sprint changeToFinish(long id) throws DataNotFoundException, InvalidDataException {
        Sprint sprintToFinish = getSprintById(id);

        if (!sprintToFinish.isActive()) {
            throw new InvalidDataException("This sprint isn't even active");
        }

        sprintToFinish.setActive(false);
        return sprintToFinish;
    }

    @Transactional
    public Sprint updateSprint(Sprint sprint) throws DataNotFoundException {
        Sprint updatedSprint = getSprintById(sprint.getId());
        updatedSprint.setName(sprint.getName());
        updatedSprint.setStartDate(sprint.getStartDate());
        updatedSprint.setEndDate(sprint.getEndDate());
        updatedSprint.setStoryPointsToSpend(sprint.getStoryPointsToSpend());
        return updatedSprint;
    }
}
