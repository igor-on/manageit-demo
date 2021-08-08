package com.in.demo.manage.manageit.repository;

import com.in.demo.manage.manageit.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {

    @Query("select s from Sprint s left join fetch s.tasks")
    List<Sprint> findAllSprints();
}
