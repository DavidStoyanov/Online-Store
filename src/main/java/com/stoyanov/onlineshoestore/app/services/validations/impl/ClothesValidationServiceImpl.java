package com.stoyanov.onlineshoestore.app.services.validations.impl;

import com.stoyanov.onlineshoestore.app.models.service.offer.clothes.ClothesSaveServiceModel;
import com.stoyanov.onlineshoestore.app.repositories.ClothesRepository;
import com.stoyanov.onlineshoestore.app.services.validations.ClothesValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClothesValidationServiceImpl extends BaseOfferValidationService
        implements ClothesValidationService {

    private final ClothesRepository clothesRepository;

    @Autowired
    public ClothesValidationServiceImpl(ClothesRepository clothesRepository) {
        this.clothesRepository = clothesRepository;
    }

    @Override
    public boolean isValid(ClothesSaveServiceModel serviceModel) {
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
