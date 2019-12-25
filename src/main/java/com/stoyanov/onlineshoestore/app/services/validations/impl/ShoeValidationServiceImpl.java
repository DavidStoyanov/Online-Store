package com.stoyanov.onlineshoestore.app.services.validations.impl;

import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeSaveServiceModel;
import com.stoyanov.onlineshoestore.app.repositories.ShoeRepository;
import com.stoyanov.onlineshoestore.app.services.validations.ShoeValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoeValidationServiceImpl extends BaseOfferValidationService
        implements ShoeValidationService {

    private final ShoeRepository shoeRepository;

    @Autowired
    public ShoeValidationServiceImpl(ShoeRepository shoeRepository) {
        this.shoeRepository = shoeRepository;
    }

    @Override
    public boolean isValid(ShoeSaveServiceModel serviceModel) {
        boolean valid = super.isValid(serviceModel);

        //todo: validate return valid bool
        return valid;
    }

    @Override
    public boolean isValid(String id) {
        //todo: validate
        return true;
    }
}
