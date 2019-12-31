package com.stoyanov.onlineshoestore.app.services.offers.base;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface GenericOfferService<S, D> {

    @PreAuthorize("hasAnyAuthority('ROOT', 'MODERATOR')")
    void create(S serviceModel);

    @PreAuthorize("hasAnyAuthority('ROOT', 'MODERATOR')")
    void edit(S serviceModel);

    @PreAuthorize("hasAnyAuthority('ROOT', 'MODERATOR')")
    void delete(String id);

    D getOneById(String id);

    List<D> getAll();
}
