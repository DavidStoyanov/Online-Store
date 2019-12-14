package com.stoyanov.onlineshoestore.app.errors;

public class OfferNotFoundException extends NullPointerException {

    public OfferNotFoundException() {
    }

    public OfferNotFoundException(String s) {
        super(s);
    }
}
