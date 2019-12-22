package com.stoyanov.onlineshoestore.app.services.offer.impl;

import com.stoyanov.onlineshoestore.app.models.service.offer.clothes.ClothesCreateServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.clothes.ClothesDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.clothes.ClothesEditServiceModel;
import com.stoyanov.onlineshoestore.app.services.offer.OfferService;

import java.util.List;

public class ClothesServiceImpl implements OfferService<ClothesCreateServiceModel, ClothesEditServiceModel, ClothesDetailsServiceModel> {
    

    @Override
    public void create(ClothesCreateServiceModel serviceModel) {

    }

    @Override
    public void edit(ClothesEditServiceModel serviceModel) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public ClothesDetailsServiceModel getOneById(String id) {
        return null;
    }

    @Override
    public List<ClothesDetailsServiceModel> getAll() {
        return null;
    }
}
