package com.stoyanov.onlineshoestore.app.services.factories;

import com.stoyanov.onlineshoestore.app.models.entities.offer.Attribute;

public interface AttributeFactory {

    Attribute create(String name);
}
