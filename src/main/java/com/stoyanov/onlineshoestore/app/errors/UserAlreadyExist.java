package com.stoyanov.onlineshoestore.app.errors;

public class UserAlreadyExist extends Exception {

    public UserAlreadyExist() {
    }

    public UserAlreadyExist(String message) {
        super(message);
    }
}
