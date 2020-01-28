package com.stoyanov.onlineshoestore.app.services.factories;

import com.stoyanov.onlineshoestore.app.models.entities.offer.Attribute;
import com.stoyanov.onlineshoestore.app.models.entities.offer.OfferAttribute;

public interface OfferAttributeFactory {

    OfferAttribute create();

    OfferAttribute create(Attribute attribute);
}
