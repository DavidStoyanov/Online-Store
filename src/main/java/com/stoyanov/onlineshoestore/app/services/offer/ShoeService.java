package com.stoyanov.onlineshoestore.app.services.offer;

import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeSize;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeType;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeCreateServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeEditServiceModel;

import java.util.List;

public interface ShoeService extends OfferService<ShoeCreateServiceModel,ShoeEditServiceModel, ShoeDetailsServiceModel> {

    List<ShoeSize> getShoeSizes();

    List<ShoeType> getShoeTypes();
}
