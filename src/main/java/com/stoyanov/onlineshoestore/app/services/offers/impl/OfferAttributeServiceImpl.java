package com.stoyanov.onlineshoestore.app.services.offers.impl;

import com.stoyanov.onlineshoestore.app.errors.offer.OfferNotFoundException;
import com.stoyanov.onlineshoestore.app.models.entities.offer.Attribute;
import com.stoyanov.onlineshoestore.app.models.entities.offer.Offer;
import com.stoyanov.onlineshoestore.app.models.entities.offer.OfferAttribute;
import com.stoyanov.onlineshoestore.app.repositories.OfferAttributeRepository;
import com.stoyanov.onlineshoestore.app.repositories.OfferRepository;
import com.stoyanov.onlineshoestore.app.services.factories.OfferAttributeFactory;
import com.stoyanov.onlineshoestore.app.services.offers.OfferAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferAttributeServiceImpl implements OfferAttributeService {

    private final OfferAttributeRepository offerAttributeRepository;
    private final OfferRepository offerRepository;

    private final OfferAttributeFactory factory;

    @Autowired
    public OfferAttributeServiceImpl(OfferAttributeRepository offerAttributeRepository,
                                     OfferRepository offerRepository,
                                     OfferAttributeFactory factory) {
        this.offerAttributeRepository = offerAttributeRepository;
        this.offerRepository = offerRepository;
        this.factory = factory;
    }

    @Override
    public boolean contains(String attr, String offerId) {
        return this.offerAttributeRepository.existsByAttributeNameAndOfferId(attr, offerId);
    }

    @Override
    public void create(Attribute attribute, String offerId) throws OfferNotFoundException {
        OfferAttribute offerAttribute = factory.create();

        Offer offer = this.offerRepository.findById(offerId)
                .orElseThrow(OfferNotFoundException::new);
        offerAttribute.setOffer(offer);

        this.offerAttributeRepository.saveAndFlush(offerAttribute);
    }

    @Override
    public void delete(String attr, String offerId) {
        this.offerAttributeRepository.deleteByAttributeNameAndOfferId(attr, offerId);
    }
}
