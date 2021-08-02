package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.error.DataNotFoundException;
import com.in.demo.manage.manageit.error.InvalidDataException;
import com.in.demo.manage.manageit.error.UserExistsException;
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

    public User getUserByUsername(String username) throws UserNotFoundException {
        return repository.findById(username)
                .orElseThrow(() -> new UserNotFoundException(
                        "There is no such user with this username: " + username));
    }

    public User addNewUser(User user) throws UserNotFoundException, UserExistsException {
        if (user == null) {
            throw new UserNotFoundException("There is no user to add!");
        }

        return repository.save(user);
    }

    public void deleteUser(String username) {
        repository.deleteById(username);
    }

    @Transactional
    public User updateUser(User user) throws UserNotFoundException {
        User updatedUser = getUserByUsername(user.getUsername());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setProjects(user.getProjects());
        return updatedUser;
    }

}
