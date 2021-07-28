package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.error.UserNotFoundException;
import com.in.demo.manage.manageit.model.User;
import com.in.demo.manage.manageit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("There is no such user"));
    }

    public User addNewUser(User user) throws UserNotFoundException {
        if (user == null) {
            throw new UserNotFoundException("There is no user to add");
        }
        if (user.getId() != null) {
            throw new IllegalArgumentException("Id is auto-generated, cannot be created manually");
        }
        return repository.save(user);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public User updateUser(User user) throws UserNotFoundException {
        User updatedUser = getUserById(user.getId());
        updatedUser.setUsername(user.getUsername());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setProjects(user.getProjects());
        return updatedUser;
    }
}