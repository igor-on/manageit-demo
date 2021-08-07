package com.in.demo.manage.manageit.model;

import lombok.Getter;

@Getter
public enum Priority {
    NOT_AT_ALL(0),
    KINDA_IMPORTANT(1),
    IMPORTANT(2),
    VERY_IMPORTANT(3),
    ASAP(4);

    Integer decimal;


    Priority(Integer decimal) {
        this.decimal = decimal;
    }
}
