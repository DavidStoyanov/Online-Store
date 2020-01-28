package com.stoyanov.onlineshoestore.app.services.offers.impl;

import com.stoyanov.onlineshoestore.app.errors.offer.AttributeNotFoundException;
import com.stoyanov.onlineshoestore.app.errors.offer.OfferAttributeNotFoundException;
import com.stoyanov.onlineshoestore.app.models.entities.offer.Attribute;
import com.stoyanov.onlineshoestore.app.models.entities.offer.Offer;
import com.stoyanov.onlineshoestore.app.models.entities.offer.OfferAttribute;
import com.stoyanov.onlineshoestore.app.repositories.AttributeRepository;
import com.stoyanov.onlineshoestore.app.repositories.OfferAttributeRepository;
import com.stoyanov.onlineshoestore.app.services.offers.AttributeService;
import com.stoyanov.onlineshoestore.app.services.offers.OfferAttributeService;
import com.stoyanov.onlineshoestore.app.services.offers.OfferManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferManageServiceImpl implements OfferManageService {

    private final AttributeRepository attributeRepository;
    private final OfferAttributeRepository offerAttributeRepository;

    private final AttributeService attributeService;
    private final OfferAttributeService offerAttributeService;

    @Autowired
    public OfferManageServiceImpl(AttributeRepository attributeRepository,
                                  OfferAttributeRepository offerAttributeRepository,
                                  AttributeService attributeService,
                                  OfferAttributeService offerAttributeService) {
        this.attributeRepository = attributeRepository;
        this.offerAttributeRepository = offerAttributeRepository;
        this.attributeService = attributeService;
        this.offerAttributeService = offerAttributeService;
    }

    @Override
    public OfferAttribute createAttr(String attr, String value) throws AttributeNotFoundException {
        Attribute attribute;
        if (!this.attributeService.contains(attr)) {
            attribute = new Attribute(attr);
            this.attributeRepository.saveAndFlush(attribute);
        } else {
            attribute = this.attributeRepository.findByName(attr)
                    .orElseThrow(AttributeNotFoundException::new);
        }

        OfferAttribute offerAttribute = new OfferAttribute(attribute);
        this.offerAttributeRepository.saveAndFlush(offerAttribute);

        offerAttribute.setValue(value);

        return offerAttribute;
    }

    @Override
    public OfferAttribute editAttr(String attr, String value, String offerId) throws AttributeNotFoundException, OfferAttributeNotFoundException {
        Attribute attribute;
        if (!this.attributeService.contains(attr)) {
            attribute = new Attribute(attr);
            this.attributeRepository.saveAndFlush(attribute);
        } else {
            attribute = this.attributeRepository.findByName(attr)
                    .orElseThrow(AttributeNotFoundException::new);
        }

        OfferAttribute offerAttribute;
        if (!this.offerAttributeService.contains(attr, offerId)) {
            offerAttribute = new OfferAttribute(attribute);
            this.offerAttributeRepository.saveAndFlush(offerAttribute);
        } else {
            offerAttribute = this.offerAttributeRepository
                    .findByAttributeNameAndOfferId(attr, offerId)
                    .orElseThrow(OfferAttributeNotFoundException::new);
        }

        offerAttribute.setValue(value);

        return offerAttribute;
    }

    @Override
    public void deleteAttr(String attr, Offer offer) throws OfferAttributeNotFoundException {
        OfferAttribute offerAttribute = this.offerAttributeRepository
                .findByAttributeNameAndOfferId(attr, offer.getId())
                .orElseThrow(OfferAttributeNotFoundException::new);

        offerAttribute.setOffer(null);
        offer.getAttributes().remove(offerAttribute);
        this.offerAttributeRepository.delete(offerAttribute);
    }
}
