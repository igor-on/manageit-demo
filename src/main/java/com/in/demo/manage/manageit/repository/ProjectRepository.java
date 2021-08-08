package com.in.demo.manage.manageit.repository;

import com.in.demo.manage.manageit.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("select p from Project p left join fetch p.sprints")
    List<Project> findAllProjects();
}
