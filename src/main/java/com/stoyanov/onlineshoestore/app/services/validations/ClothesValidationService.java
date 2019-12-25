package com.stoyanov.onlineshoestore.app.services.validations;

import com.stoyanov.onlineshoestore.app.models.service.offer.clothes.ClothesSaveServiceModel;

public interface ClothesValidationService {

    boolean isValid(ClothesSaveServiceModel serviceModel);

    boolean isValid(String id);
}
