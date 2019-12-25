package com.stoyanov.onlineshoestore.app.services.offers;

import com.stoyanov.onlineshoestore.app.models.service.offer.offer.OfferDetailsServiceModel;

import java.util.List;

public interface OfferService {

    List<OfferDetailsServiceModel> getAll();
}
