package com.stoyanov.onlineshoestore.app.errors.user;

public class UserNotFoundException extends NullPointerException {

    private static final String EXCEPTION_MESSAGE = "User not found!";

    public UserNotFoundException() {
        this(EXCEPTION_MESSAGE);
    }

    public UserNotFoundException(String s) {
        super(s);
    }
}
