package com.in.demo.manage.manageit.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {

    private final String username;
    private final String password;
}
