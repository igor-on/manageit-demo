package com.in.demo.manage.manageit.model;

import lombok.Getter;

@Getter
public enum Priority {
    ONE(0),
    TWO(1),
    THREE(2),
    FOUR(3),
    FIVE(4);

    Integer decimal;

    Priority(Integer decimal) {
        this.decimal = decimal;
    }
}
