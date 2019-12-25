package com.stoyanov.onlineshoestore.app.services.validations;

import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeSaveServiceModel;

public interface ShoeValidationService {

    boolean isValid(ShoeSaveServiceModel serviceModel);

    boolean isValid(String id);
}
