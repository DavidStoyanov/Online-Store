package com.stoyanov.onlineshoestore.app.services;

import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeCreateServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeEditServiceModel;

public interface ShoeService {

    void createByUser(ShoeCreateServiceModel serviceModel, String username);

    void editByUser(ShoeEditServiceModel serviceModel, String username);

    void deleteByUsername(String offerId, String username);

    ShoeEditServiceModel getOneById(String id);
}
