package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.error.UserNotFoundException;
import com.in.demo.manage.manageit.model.User;
import com.in.demo.manage.manageit.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.in.demo.manage.manageit.data.TestsData.generateSampleUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    void testGetAllUsers() {
        var u1 = generateSampleUser();
        var u2 = generateSampleUser();
        when(repository.findAll()).thenReturn(List.of(u1, u2));

        List<User> actual = service.getAllUsers();

        assertThat(actual)
                .hasSize(2)
                .containsExactly(u1, u2)
                .doesNotContainNull();

        assertThat(actual.get(0))
                .isEqualTo(u1);
    }

    @Test
    void testGetUserById_WhenSuccess() throws UserNotFoundException {
        User u1 = generateSampleUser();

        when(repository.findById(u1.getId())).thenReturn(Optional.of(u1));

        User actual = service.getUserById(u1.getId());

        assertThat(actual).isEqualTo(u1);
    }

    @Test
    void testGetUserById_ShouldThrowException_WhenNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Throwable throwable = Assertions.assertThrows(UserNotFoundException.class,
                () -> service.getUserById(anyLong()));

        assertThat(throwable).isExactlyInstanceOf(UserNotFoundException.class);
    }

    @Test
    void testAddNewUser_WhenSuccess() throws UserNotFoundException {
        var user = generateSampleUser();
        user.setId(null);

        when(repository.save(user)).thenReturn(generateSampleUser());

        User actual = service.addNewUser(user);

        assertNotNull(actual.getId());
        assertEquals(actual.getUsername(), user.getUsername());
        assertEquals(actual.getPassword(), user.getPassword());
        assertEquals(actual.getProjects(), user.getProjects());
    }

    @Test
    void testAddNewUser_ShouldThrowException_WhenIdIsNotNull() {
        var user = generateSampleUser();
        assertThrows(IllegalArgumentException.class, () -> service.addNewUser(user));
    }

    @Test
    void testDeleteUser_WhenSuccess() {
        var user = generateSampleUser();

        service.deleteUser(user.getId());

        verify(repository, times(1)).deleteById(user.getId());
    }

    @Test
    void testDeleteUser_WhenNotExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.getUserById(10000L));
    }

    @Test
    void testUpdateUser_WhenSuccess() throws UserNotFoundException {
        var u1 = generateSampleUser();
        var u2 = generateSampleUser();

        when(repository.findById(u1.getId())).thenReturn(Optional.of(u1));
        u2 = service.updateUser(u1);

        assertEquals(u1.getId(), u2.getId());
        assertEquals(u1.getUsername(), u2.getUsername());
        assertEquals(u1.getPassword(), u2.getPassword());
        assertEquals(u1.getProjects(), u2.getProjects());
    }
}
