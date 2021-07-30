package com.in.demo.manage.manageit.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@Builder
public class UserDTO {

    private final Long id;
    private final String username;
    private final String password;
}
