package com.stoyanov.onlineshoestore.app.services;

import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.OfferCreateServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.OfferEditServiceModel;

public interface ShoeService {

    void createByUser(OfferCreateServiceModel serviceModel, String username);

    OfferEditServiceModel getOneById(String id);
}
