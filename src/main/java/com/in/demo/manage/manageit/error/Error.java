package com.in.demo.manage.manageit.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Error {

    private String message;
    private LocalDateTime dateTime;
    private Integer status;
}
