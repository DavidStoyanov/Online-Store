package com.stoyanov.onlineshoestore.app.repositories;

import com.stoyanov.onlineshoestore.app.models.entities.offer.Attribute;
import com.stoyanov.onlineshoestore.app.models.entities.offer.Offer;
import com.stoyanov.onlineshoestore.app.models.entities.offer.OfferAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfferAttributeRepository extends JpaRepository<OfferAttribute, String> {

    boolean existsByAttributeNameAndOfferId(String attribute, String offerId);

    Optional<OfferAttribute> findByAttributeNameAndOfferId(String attribute, String offerId);

    Optional<OfferAttribute> findByAttributeAndOffer(Attribute attribute, Offer offer);

    Optional<OfferAttribute> findByAttributeAndOfferId(Attribute attribute, String offerId);

    void deleteByAttributeNameAndOfferId(String attribute, String offerId);
}
