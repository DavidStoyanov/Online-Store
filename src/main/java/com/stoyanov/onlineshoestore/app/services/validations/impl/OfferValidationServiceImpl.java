package com.stoyanov.onlineshoestore.app.services.validations.impl;

import com.stoyanov.onlineshoestore.app.models.services.offer.OfferServiceModel;
import com.stoyanov.onlineshoestore.app.services.validations.OfferValidationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.stoyanov.onlineshoestore.app.util.BigDecimalUtil.isNumberHigher;
import static com.stoyanov.onlineshoestore.app.util.BigDecimalUtil.isNumberLower;

@Service
public class OfferValidationServiceImpl implements OfferValidationService {

    @Override
    public boolean isValid(OfferServiceModel serviceModel) {
        /*if (serviceModel.getTitle().length() < 8) {
            return false;
        }

        if (serviceModel.getDescription().length() < 10) {
            return false;
        }

        if (isNumberLower(serviceModel.getPrice(), new BigDecimal(0)) ||
                isNumberHigher(serviceModel.getPrice(), new BigDecimal(10000))) {
            return false;
        }*/

        return true;
    }
}
