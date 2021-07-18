package com.in.demo.manage.manageit.error;

public class NotFoundException extends Exception {
    private String message;

    public NotFoundException(String message) {
        this.message = message;
    }
}
