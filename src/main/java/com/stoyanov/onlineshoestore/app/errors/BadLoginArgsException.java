package com.stoyanov.onlineshoestore.app.errors;

public class BadLoginArgsException extends Exception {

    public BadLoginArgsException() {
    }

    public BadLoginArgsException(String message) {
        super(message);
    }
}
