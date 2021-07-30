package com.in.demo.manage.manageit.mapper;

import com.in.demo.manage.manageit.model.Project;
import com.in.demo.manage.manageit.model.User;
import com.in.demo.manage.manageit.model.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO mapToUserDTO(User user) {

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
