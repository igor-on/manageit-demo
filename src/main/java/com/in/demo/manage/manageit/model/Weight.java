package com.in.demo.manage.manageit.model;

public enum Weight {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    Integer decimal;

    Weight(Integer decimal) {
        this.decimal = decimal;
    }
}