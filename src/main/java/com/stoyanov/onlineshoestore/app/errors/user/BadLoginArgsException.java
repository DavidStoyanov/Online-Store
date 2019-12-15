package com.stoyanov.onlineshoestore.app.errors.user;

public class BadLoginArgsException extends Exception {

    public BadLoginArgsException() {
    }

    public BadLoginArgsException(String message) {
        super(message);
    }
}
