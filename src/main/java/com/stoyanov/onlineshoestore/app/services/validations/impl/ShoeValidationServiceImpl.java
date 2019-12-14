package com.stoyanov.onlineshoestore.app.services.validations.impl;

import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeCreateServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeEditServiceModel;
import com.stoyanov.onlineshoestore.app.repositories.ShoeRepository;
import com.stoyanov.onlineshoestore.app.services.validations.ShoeValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoeValidationServiceImpl implements ShoeValidationService {

    private final ShoeRepository shoeRepository;

    @Autowired
    public ShoeValidationServiceImpl(ShoeRepository shoeRepository) {
        this.shoeRepository = shoeRepository;
    }

    @Override
    public boolean isValid(ShoeCreateServiceModel serviceModel) {
        //todo: validate
        return true;
    }

    @Override
    public boolean isValid(ShoeEditServiceModel serviceModel, String username) {
        boolean isOwnerOfOffer = this.shoeRepository
                .existsByIdAndCreatedBy_Username(serviceModel.getId(), username);
        return true;
    }
}
