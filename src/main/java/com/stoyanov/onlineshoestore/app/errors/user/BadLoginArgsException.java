package com.stoyanov.onlineshoestore.app.errors.user;

public class BadLoginArgsException extends Exception {

    private static final String EXCEPTION_MESSAGE = "Invalid username or password";

    public BadLoginArgsException() {
        super(EXCEPTION_MESSAGE);
    }

    public BadLoginArgsException(String message) {
        super(message);
    }
}
