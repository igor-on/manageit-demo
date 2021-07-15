package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SprintService {

    private final SprintRepository repository;

    public Sprint saveSprint(Sprint sprint) {
        return repository.save(sprint);
    }
}
