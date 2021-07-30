package com.in.demo.manage.manageit.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserDTO {

    private final String username;
    private final String password;
    private final List<Long> projectsIds;
}