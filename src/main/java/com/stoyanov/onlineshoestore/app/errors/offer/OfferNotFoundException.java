package com.stoyanov.onlineshoestore.app.errors.offer;

public class OfferNotFoundException extends NullPointerException {

    public OfferNotFoundException() {
    }

    public OfferNotFoundException(String s) {
        super(s);
    }
}
