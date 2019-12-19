package com.stoyanov.onlineshoestore.app.errors.offer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OfferNotFoundException extends NullPointerException {

    private static final String EXCEPTION_MESSAGE = "Offer has expired";

    public OfferNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }

    public OfferNotFoundException(String message) {
        super(message);
    }
}
