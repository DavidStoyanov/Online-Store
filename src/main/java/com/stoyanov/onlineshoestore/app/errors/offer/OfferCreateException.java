package com.stoyanov.onlineshoestore.app.errors.offer;

public class OfferCreateException extends RuntimeException {

    public OfferCreateException() {
    }

    public OfferCreateException(String message) {
        super(message);
    }
}
