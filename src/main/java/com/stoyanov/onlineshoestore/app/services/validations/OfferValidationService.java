package com.stoyanov.onlineshoestore.app.services.validations;

import com.stoyanov.onlineshoestore.app.models.services.offer.OfferServiceModel;

public interface OfferValidationService {

    boolean isValid(OfferServiceModel model);
}
