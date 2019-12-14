package com.stoyanov.onlineshoestore.app.errors;

public class OfferCreateException extends RuntimeException {

    public OfferCreateException() {
    }

    public OfferCreateException(String message) {
        super(message);
    }
}
