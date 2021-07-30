package com.in.demo.manage.manageit.repository;

import com.in.demo.manage.manageit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   Optional<User> getUserByUsername(String username);
}
