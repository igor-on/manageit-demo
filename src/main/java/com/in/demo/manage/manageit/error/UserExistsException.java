package com.in.demo.manage.manageit.error;

public class UserExistsException extends Exception {

    public UserExistsException(final String message) {
        super(message);
    }
}
