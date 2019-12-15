package com.stoyanov.onlineshoestore.app.errors.user;

public class UserAlreadyExist extends Exception {

    public UserAlreadyExist() {
    }

    public UserAlreadyExist(String message) {
        super(message);
    }
}
