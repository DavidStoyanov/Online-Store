package com.stoyanov.onlineshoestore.app.services.offers.impl;

import com.stoyanov.onlineshoestore.app.models.service.offer.offer.OfferDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.repositories.OfferRepository;
import com.stoyanov.onlineshoestore.app.services.offers.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    private final ModelMapper mapper;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper mapper) {
        this.offerRepository = offerRepository;
        this.mapper = mapper;
    }

    @Override
    public List<OfferDetailsServiceModel> getAll() {
        return this.offerRepository.findAll().stream()
                .map(offer -> this.mapper.map(offer, OfferDetailsServiceModel.class))
                .collect(Collectors.toList());
    }
}
