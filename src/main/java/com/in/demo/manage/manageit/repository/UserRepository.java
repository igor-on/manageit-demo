package com.in.demo.manage.manageit.repository;

import com.in.demo.manage.manageit.model.Sprint;
import com.in.demo.manage.manageit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u left join fetch u.sprints")
    List<User> findAllUsers();
}
