package com.in.demo.manage.manageit.mapper;

import com.in.demo.manage.manageit.model.User;
import com.in.demo.manage.manageit.model.dto.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    @Test
    void that_mapToUserDTO_worksCorrectly() {
        User testUser = new User("username", "password", new ArrayList<>(), true);

        UserDTO actual = UserMapper.mapToUserDTO(testUser);

        assertThat(actual.getUsername()).isEqualTo("username");
        assertThat(actual.getPassword()).isEqualTo("password");
    }
}
