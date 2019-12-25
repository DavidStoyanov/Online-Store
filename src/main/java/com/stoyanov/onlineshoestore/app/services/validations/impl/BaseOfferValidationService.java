package com.stoyanov.onlineshoestore.app.services.validations.impl;

import com.stoyanov.onlineshoestore.app.models.service.offer.base.OfferSaveServiceModel;

import java.math.BigDecimal;


public class BaseOfferValidationService {

    public boolean isValid(OfferSaveServiceModel offerModel) {
        //todo: make variables (booleans) return bool
        if (offerModel.getPrice().compareTo(new BigDecimal(0)) > 0 &&
                offerModel.getPrice().compareTo(new BigDecimal(10000)) <= 0) {
            return false;
        }

        if (offerModel.getTitle().isBlank()) {
            return false;
        }

        return true;
    }
}
