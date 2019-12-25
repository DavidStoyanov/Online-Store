package com.stoyanov.onlineshoestore.app.repositories;

import com.stoyanov.onlineshoestore.app.models.entity.offer.BaseOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<BaseOffer, String> {
}
