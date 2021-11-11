package com.in.demo.manage.manageit.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Error {

    private final String message;
    private final LocalDateTime dateTime;
    private final Integer status;
}
