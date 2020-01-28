package com.stoyanov.onlineshoestore.app.services.factories.impl;

import com.stoyanov.onlineshoestore.app.annotations.Factory;
import com.stoyanov.onlineshoestore.app.services.factories.HeadersFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Factory
public class HeadersFactoryImpl implements HeadersFactory {

    @Override
    public HttpHeaders withLocation(Object id) {
        HttpHeaders headers = new HttpHeaders();
        URI uri = ServletUriComponentsBuilder.fromPath("offer/details")
                .path("/{id}").buildAndExpand(id).toUri();
        headers.setLocation(uri);
        headers.add("Content-Type", "application/json");
        return headers;
    }

    @Override
    public HttpHeaders offerNotFound() {
        HttpHeaders headers = new HttpHeaders();
        URI uri = ServletUriComponentsBuilder
                .fromUriString("/offers").build().toUri();
        headers.setLocation(uri);
        return headers;
    }
}
