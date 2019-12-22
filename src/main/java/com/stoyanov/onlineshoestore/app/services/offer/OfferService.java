package com.stoyanov.onlineshoestore.app.services.offer;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface OfferService<C, U, R> {

    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ROOT')")
    void create(C serviceModel);

    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ROOT')")
    void edit(U serviceModel);

    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ROOT')")
    void delete(String id);

    R getOneById(String id);

    List<R> getAll();
}
