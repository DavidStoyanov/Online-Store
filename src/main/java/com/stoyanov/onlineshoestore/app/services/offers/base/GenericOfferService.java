package com.stoyanov.onlineshoestore.app.services.offers.base;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface GenericOfferService<S, D> {

    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ROOT')")
    void create(S serviceModel);

    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ROOT')")
    void edit(S serviceModel);

    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ROOT')")
    void delete(String id);

    D getOneById(String id);

    List<D> getAll();
}
