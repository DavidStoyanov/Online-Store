package com.stoyanov.onlineshoestore.app.services.offers;

import com.stoyanov.onlineshoestore.app.errors.offer.OfferNotFoundException;
import com.stoyanov.onlineshoestore.app.models.services.offer.OfferServiceModel;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface OfferService {

    @PreAuthorize("hasAnyAuthority('ROOT', 'MODERATOR')")
    String create(OfferServiceModel serviceModel);

    @PreAuthorize("hasAnyAuthority('ROOT', 'MODERATOR')")
    String edit(OfferServiceModel serviceModel, String id) throws OfferNotFoundException;

    @PreAuthorize("hasAnyAuthority('ROOT', 'MODERATOR')")
    void delete(String id) throws OfferNotFoundException;

    OfferServiceModel getById(String id) throws OfferNotFoundException;

    List<OfferServiceModel> getAll();
}
