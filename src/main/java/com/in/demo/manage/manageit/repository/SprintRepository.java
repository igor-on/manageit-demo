package com.in.demo.manage.manageit.repository;

import com.in.demo.manage.manageit.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {
}
