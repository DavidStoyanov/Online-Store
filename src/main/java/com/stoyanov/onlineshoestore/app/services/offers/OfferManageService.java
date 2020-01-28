package com.stoyanov.onlineshoestore.app.services.offers;

import com.stoyanov.onlineshoestore.app.errors.offer.AttributeNotFoundException;
import com.stoyanov.onlineshoestore.app.errors.offer.OfferAttributeNotFoundException;
import com.stoyanov.onlineshoestore.app.models.entities.offer.Offer;
import com.stoyanov.onlineshoestore.app.models.entities.offer.OfferAttribute;

public interface OfferManageService {

    OfferAttribute createAttr(String attr, String value) throws AttributeNotFoundException;

    OfferAttribute editAttr(String attr, String value, String offerId) throws AttributeNotFoundException, OfferAttributeNotFoundException;

    void deleteAttr(String attr, Offer offer) throws OfferAttributeNotFoundException;
}
