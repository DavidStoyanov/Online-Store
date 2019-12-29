package com.stoyanov.onlineshoestore.app.services.validations.impl;

import com.stoyanov.onlineshoestore.app.models.service.offer.base.OfferSaveServiceModel;

import java.math.BigDecimal;

import static com.stoyanov.onlineshoestore.app.util.BigDecimalUtil.isNumberHigher;
import static com.stoyanov.onlineshoestore.app.util.BigDecimalUtil.isNumberLower;


public class BaseOfferValidationService {

    protected boolean isValid(OfferSaveServiceModel offerModel) {
        if (offerModel.getTitle().length() < 8) {
            return false;
        }

        if (offerModel.getDescription().length() < 10) {
            return false;
        }

        if (isNumberLower(offerModel.getPrice(), new BigDecimal(0)) ||
                isNumberHigher(offerModel.getPrice(), new BigDecimal(10000))) {
            return false;
        }

        return true;
    }
}
