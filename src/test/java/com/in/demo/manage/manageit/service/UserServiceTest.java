package com.in.demo.manage.manageit.service;

import com.in.demo.manage.manageit.error.UserExistsException;
import com.in.demo.manage.manageit.error.UserNotFoundException;
import com.in.demo.manage.manageit.model.Authorities;
import com.in.demo.manage.manageit.model.User;
import com.in.demo.manage.manageit.repository.AuthRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;
    @Mock
    private AuthRepository authRepository;

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
    void testGetUserByUsername_WhenSuccess() throws UserNotFoundException {
        User u1 = generateSampleUser();

        when(repository.findById(u1.getUsername())).thenReturn(Optional.of(u1));

        User actual = service.getUserByUsername(u1.getUsername());

        assertThat(actual).isEqualTo(u1);
    }

    @Test
    void testGetUserByUsername_ShouldThrowException_WhenNotFound() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Throwable throwable = Assertions.assertThrows(UserNotFoundException.class,
                () -> service.getUserByUsername(any()));

        assertThat(throwable).isExactlyInstanceOf(UserNotFoundException.class);
    }

    @Test
    void testAddNewUser_WhenSuccess() throws UserNotFoundException, UserExistsException {
        var user = generateSampleUser();

        when(authRepository.save(any())).thenReturn(new Authorities());
        when(repository.save(user)).thenReturn(user);

        User actual = service.addNewUser(user);

        assertEquals(actual.getUsername(), user.getUsername());
        assertEquals(actual.getPassword(), user.getPassword());
        assertEquals(actual.getProjects(), user.getProjects());
    }

    @Test
    void testDeleteUser_WhenSuccess() {
        var user = generateSampleUser();

        service.deleteUser(user.getUsername());

        verify(repository, times(1)).deleteById(user.getUsername());
    }

    @Test
    void testDeleteUser_WhenNotExist() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.getUserByUsername("manage"));
    }

    @Test
    void testUpdateUser_WhenSuccess() throws UserNotFoundException {
        var u1 = generateSampleUser();
        var u2 = generateSampleUser();

        when(repository.findById(u1.getUsername())).thenReturn(Optional.of(u1));
        u2 = service.updateUser(u1);

        assertEquals(u1.getUsername(), u2.getUsername());
        assertEquals(u1.getPassword(), u2.getPassword());
        assertEquals(u1.getProjects(), u2.getProjects());
    }
}
