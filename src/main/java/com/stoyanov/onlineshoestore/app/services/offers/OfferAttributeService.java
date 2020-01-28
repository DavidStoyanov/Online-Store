package com.stoyanov.onlineshoestore.app.services.offers;

import com.stoyanov.onlineshoestore.app.errors.offer.OfferNotFoundException;
import com.stoyanov.onlineshoestore.app.models.entities.offer.Attribute;

public interface OfferAttributeService {

    boolean contains(String attr, String offerId);

    void create(Attribute attribute, String offerId) throws OfferNotFoundException;

    void delete(String attr, String offerId);
}
