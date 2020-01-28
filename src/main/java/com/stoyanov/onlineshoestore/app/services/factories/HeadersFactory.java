package com.stoyanov.onlineshoestore.app.services.factories;

import org.springframework.http.HttpHeaders;

public interface HeadersFactory {

    HttpHeaders withLocation(Object id);

    HttpHeaders offerNotFound();
}
