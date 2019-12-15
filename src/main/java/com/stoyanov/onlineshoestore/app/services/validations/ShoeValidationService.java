package com.stoyanov.onlineshoestore.app.services.validations;

import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeCreateServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeEditServiceModel;

public interface ShoeValidationService {

    boolean isValid(ShoeCreateServiceModel serviceModel);

    boolean isValid(ShoeEditServiceModel serviceModel, String username);

    boolean isValid(String offerId, String username);
}
