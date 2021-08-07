package com.in.demo.manage.manageit.repository;

import com.in.demo.manage.manageit.model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Authorities, String> {
}
