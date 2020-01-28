package com.stoyanov.onlineshoestore.app.services.offers;

import com.stoyanov.onlineshoestore.app.models.entities.offer.Attribute;

public interface AttributeService {

    boolean contains(String name);

    void create(String name);

    Attribute getByName(String name);
}
