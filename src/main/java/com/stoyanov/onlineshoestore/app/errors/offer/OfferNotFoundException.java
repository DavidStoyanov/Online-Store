package com.stoyanov.onlineshoestore.app.errors.offer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Offer not found.")
public class OfferNotFoundException extends RuntimeException {
}
