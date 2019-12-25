package com.stoyanov.onlineshoestore.app.services.offers;

import com.stoyanov.onlineshoestore.app.models.service.offer.clothes.ClothesDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.clothes.ClothesSaveServiceModel;
import com.stoyanov.onlineshoestore.app.services.offers.base.GenericOfferService;

public interface ClothesService extends GenericOfferService<ClothesSaveServiceModel, ClothesDetailsServiceModel> {
}
