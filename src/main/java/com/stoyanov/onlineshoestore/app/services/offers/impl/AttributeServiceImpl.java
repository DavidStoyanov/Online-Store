package com.stoyanov.onlineshoestore.app.services.offers.impl;

import com.stoyanov.onlineshoestore.app.models.entities.offer.Attribute;
import com.stoyanov.onlineshoestore.app.repositories.AttributeRepository;
import com.stoyanov.onlineshoestore.app.services.factories.AttributeFactory;
import com.stoyanov.onlineshoestore.app.services.offers.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttributeServiceImpl implements AttributeService {

    private final AttributeRepository attributeRepository;
    private final AttributeFactory factory;

    @Autowired
    public AttributeServiceImpl(AttributeRepository attributeRepository,
                                AttributeFactory factory) {
        this.attributeRepository = attributeRepository;
        this.factory = factory;
    }

    @Override
    public boolean contains(String name) {
        return this.attributeRepository.existsByName(name);
    }

    @Override
    public void create(String name) {
        Attribute attribute = this.factory.create(name);
        this.attributeRepository.saveAndFlush(attribute);
    }

    @Override
    public Attribute getByName(String name) {
        return this.attributeRepository.findByName(name).orElse(null);
    }
}
