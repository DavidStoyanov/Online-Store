package com.stoyanov.onlineshoestore.app.controllers;

import com.stoyanov.onlineshoestore.app.models.service.offer.offer.OfferDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.services.offers.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OffersController {

    private final OfferService offerService;
    private final ModelMapper mapper;

    @Autowired
    public OffersController(OfferService offerService, ModelMapper mapper) {
        this.offerService = offerService;
        this.mapper = mapper;
    }

    @GetMapping("/offers")
    public ModelAndView offers(ModelAndView mav) {
        List<OfferDetailsServiceModel> offers = this.offerService.getAll().stream()
                .map(serviceModel -> this.mapper.map(serviceModel, OfferDetailsServiceModel.class))
                .collect(Collectors.toList());
        mav.addObject("offers", offers);
        mav.setViewName("offers/offers.html");
        return mav;
    }
}
