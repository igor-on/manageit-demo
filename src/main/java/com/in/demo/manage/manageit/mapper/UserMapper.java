package com.in.demo.manage.manageit.mapper;

import com.in.demo.manage.manageit.model.User;
import com.in.demo.manage.manageit.model.dto.UserDTO;

public class UserMapper {

    public static UserDTO mapToUserDTO(User user) {

        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
