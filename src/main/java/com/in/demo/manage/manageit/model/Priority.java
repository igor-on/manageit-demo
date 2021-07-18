package com.in.demo.manage.manageit.model;

public enum Priority {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    Integer decimal;

    Priority(Integer decimal) {
        this.decimal = decimal;
    }
}
