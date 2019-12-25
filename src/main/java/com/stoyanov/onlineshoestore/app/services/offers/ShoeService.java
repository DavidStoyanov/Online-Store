package com.stoyanov.onlineshoestore.app.services.offers;

import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeSize;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeType;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeSaveServiceModel;
import com.stoyanov.onlineshoestore.app.services.offers.base.GenericOfferService;

import java.util.List;

public interface ShoeService extends GenericOfferService<ShoeSaveServiceModel, ShoeDetailsServiceModel> {

    List<ShoeSize> getShoeSizes();

    List<ShoeType> getShoeTypes();
}
