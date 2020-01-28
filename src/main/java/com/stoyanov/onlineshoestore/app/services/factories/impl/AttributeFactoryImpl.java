package com.stoyanov.onlineshoestore.app.services.factories.impl;

import com.stoyanov.onlineshoestore.app.annotations.Factory;
import com.stoyanov.onlineshoestore.app.models.entities.offer.Attribute;
import com.stoyanov.onlineshoestore.app.services.factories.AttributeFactory;

@Factory
public class AttributeFactoryImpl implements AttributeFactory {

    @Override
    public Attribute create(String name) {
        Attribute attribute = new Attribute();
        attribute.setName(name);

        return attribute;
    }
}
