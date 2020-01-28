package com.stoyanov.onlineshoestore.app.services.factories.impl;

import com.stoyanov.onlineshoestore.app.models.entities.offer.Attribute;
import com.stoyanov.onlineshoestore.app.models.entities.offer.OfferAttribute;
import com.stoyanov.onlineshoestore.app.annotations.Factory;
import com.stoyanov.onlineshoestore.app.services.factories.OfferAttributeFactory;

@Factory
public class OfferAttributeFactoryImpl implements OfferAttributeFactory {

    @Override
    public OfferAttribute create() {
        return new OfferAttribute();
    }

    @Override
    public OfferAttribute create(Attribute attribute) {
        OfferAttribute offerAttribute = new OfferAttribute();
        offerAttribute.setAttribute(attribute);

        return offerAttribute;
    }
}
