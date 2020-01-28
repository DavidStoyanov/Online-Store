package com.stoyanov.onlineshoestore.app.errors.user;

public class UserAlreadyExist extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "User already exist";

    public UserAlreadyExist() {
        super(EXCEPTION_MESSAGE);
    }

    public UserAlreadyExist(String message) {
        super(message);
    }
}
